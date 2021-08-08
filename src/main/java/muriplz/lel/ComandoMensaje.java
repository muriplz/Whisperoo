package muriplz.lel;

import muriplz.lel.comandos.Mensaje;
import muriplz.lel.tabs.MensajeTab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

// Esta es la "class" principal (Main class), el programa troncal.
public class ComandoMensaje extends JavaPlugin {

    // Acceso a "plugin.yml" para obtener datos como el nombre del plugin o la versión
    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();

    // TODO: Hacer el comando /responder, todavía está en modo comentario (desactivado)
  //  public HashMap<String,String> infoResponder;

    public void onEnable(){

        // TODO: Hacer el comando /responder, todavía está en modo comentario (desactivado)
     //   infoResponder = new HashMap<>();

        registrarComandos();

        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" El plugin ha sido activado. Versión: "+ChatColor.GREEN+version);
    }

    // Cuando se apaga el servidor (O el plugin se apaga)
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" El plugin ha sido desactivado.");
    }

    // Inicializo los comandos al cargar el plugin cuando se enciende el servidor
    public void registrarComandos() {
        Objects.requireNonNull(getCommand("mensaje")).setExecutor(new Mensaje(this));
        Objects.requireNonNull(getCommand("mensaje")).setTabCompleter(new MensajeTab());
    }


}
