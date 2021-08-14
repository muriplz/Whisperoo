package muriplz.lel;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.lel.comandos.Mensaje;
import muriplz.lel.comandos.Responder;
import muriplz.lel.tabs.MensajeTab;
import muriplz.lel.tabs.ResponderTab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

// Esta es la "class" principal (Main class), el programa troncal.
public class ComandoMensaje extends JavaPlugin {

    // Acceso a "plugin.yml" para obtener datos como el nombre del plugin o la versión
    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();


    private static ComandoMensaje instance;

    public HashMap<String,String> infoResponder;

    public void onEnable(){

        infoResponder = new HashMap<>();

        loadConfig();

        loadMessages();

        instance = this;

        registrarComandos();

        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" El plugin ha sido activado. Version: "+ChatColor.GREEN+version);
    }

    // Cuando se apaga el servidor (O el plugin se apaga)
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" El plugin ha sido desactivado.");
    }

    // Inicializo los comandos al cargar el plugin cuando se enciende el servidor
    public void registrarComandos() {
        Objects.requireNonNull(getCommand("mensaje")).setExecutor(new Mensaje(this));
        Objects.requireNonNull(getCommand("mensaje")).setTabCompleter(new MensajeTab());
        Objects.requireNonNull(getCommand("responder")).setExecutor(new Responder());
        Objects.requireNonNull(getCommand("responder")).setTabCompleter(new ResponderTab());
    }

    public static ComandoMensaje getInstance() {
        // Then return it.
        return instance;
    }

    void loadConfig (){
        CMFile myConfigFile = new CMFile(this, "config") {
            @Override
            public void loadDefaults() {
                addComment("¿Quieres sonido cuando recives un mensaje? Elegir:(true / false)");

                addDefault("sonido-recibir-mensaje", false);

                addComment("Web para ver las opciones: https://www.spigotmc.org/wiki/cc-sounds-list/");

                addDefault("elige-el-sonido","BLOCK_ANVIL_FALL");

                addComment("¿Quieres que los mensajes enviados entre jugadores acaben en punto? \".\" Eligir: (true / false)");

                addDefault("punto-mensaje",false);

                addComment("¿Quieres que se pueda mandar un mensaje a ti mismo? Elegir: (true / false)");
                addDefault("mensaje-ti-mismo",false);
            }

        };
        myConfigFile.load();
    }

    public static YamlConfiguration getMessages(){
        File messages = new File(getInstance().getDataFolder(), "messages.yml");
        return YamlConfiguration.loadConfiguration(messages);
    }

    void loadMessages (){
        CMFile myMessagesFile = new CMFile(this,"messages") {
            @Override
            public void loadDefaults() {
                addComment("\"& + Notación hexadecimal\" para usar colores.");
                addComment("Cuando el jugador usa únicamente \"/mensaje\".");
                addDefault("mensaje-sin-jugador","Usa /mensaje <Jugador> \"mensaje\" para mandar un mensaje.");

                addComment("Cuando el jugador no existe o no está conectado.");
                addDefault("jugador-no-encontrado","&cNo se han encontrado jugadores.");

                addComment("Cuando el jugador usa únicamente \"/mensaje <Jugador>\".");
                addDefault("escribir-mensaje","Tienes que escribir un mensaje.");

                addComment("Cuando intentas mandarte un mensaje a ti mismo.");
                addDefault("no-mensaje-mismo","No te puedes mandar mensajes a ti mismo.");

                addComment("Cuando el jugador usa /responder y no tiene a quien responder.");
                addDefault("nadie-para-responder","No tienes a nadie a quien responder.");
            }
        };
        myMessagesFile.load();
    }


}
