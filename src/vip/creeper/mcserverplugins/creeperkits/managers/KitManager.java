package vip.creeper.mcserverplugins.creeperkits.managers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import vip.creeper.mcserverplugins.creeperkits.CreeperKits;
import vip.creeper.mcserverplugins.creeperkits.Kit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by July on 2018/2/14.
 */
public class KitManager {
    private SQLiteManager sqLiteManager;

    public KitManager(CreeperKits plugin) {
        this.sqLiteManager = plugin.getSqLiteManager();
    }

    public boolean removeKit(String name) {
        try {
            PreparedStatement statement = sqLiteManager.getCon().prepareStatement("delete from kits where name = '" + name + "'");

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Kit getKit(String name) {
        try {
            PreparedStatement statement = sqLiteManager.getCon().prepareStatement("select * from kits where name = '" + name + "'");
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                byte[] byteItems = rs.getBytes("items");
                ByteArrayInputStream byteItemsIS = new ByteArrayInputStream(byteItems);
                Object objectItems;

                // 使用Bukkit专属的输入流进行转换
                BukkitObjectInputStream bukkitItemsIS = new BukkitObjectInputStream(byteItemsIS);

                objectItems = bukkitItemsIS.readObject();

                // 关闭输入流
                bukkitItemsIS.close();

                //noinspection deprecation
                return new Kit(name, (List<ItemStack>) objectItems);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean createKit(String name, List<ItemStack> items) {
        ByteArrayOutputStream byteItemsOS = new ByteArrayOutputStream();

        try {
            BukkitObjectOutputStream bukkitItemsOS = new BukkitObjectOutputStream(byteItemsOS);

            bukkitItemsOS.writeObject(items);
            bukkitItemsOS.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            PreparedStatement statement = sqLiteManager.getCon().prepareStatement("insert into kits(name, items) values(?, ?)");

            statement.setString(1, name.toLowerCase());
            statement.setBytes(2, byteItemsOS.toByteArray());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean isExistsKit(String name) {
        try {
            PreparedStatement statement = sqLiteManager.getCon().prepareStatement("select * from kits where name = '" + name.toLowerCase() + "'");
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Kit> getKits() {
        List<Kit> kits = new ArrayList<>();

        try {
            PreparedStatement statement = sqLiteManager.getCon().prepareStatement("select * from kits");
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                kits.add(getKit(rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kits;
    }
}
