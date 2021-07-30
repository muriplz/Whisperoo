package muriplz.lel;

import muriplz.lel.comandos.Mensaje;
import muriplz.lel.tabs.MensajeTab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ComandoMensaje extends JavaPlugin {
    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();

    public void onEnable(){

        Objects.requireNonNull(getCommand("mensaje")).setExecutor(new Mensaje(this));
        Objects.requireNonNull(getCommand("mensaje")).setTabCompleter(new MensajeTab());

        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" El plugin ha sido activado. Versión: "+ChatColor.GREEN+version);
    }

    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" El plugin ha sido desactivado.");
    }
}
