package muriplz.lel;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class funciones {

    public ComandoMensaje instance = ComandoMensaje.getInstance();
    // Una función que utiliza el sistema hexadecimal para usar la función ChatColor (https://foro.landacraft.es/t/colabora-economicamente-ayudanos-a-mantener-el-servidor/192) Colores y formato en los carteles > Guía de formato y colores.
    public static String color(String mensaje){
        return ChatColor.translateAlternateColorCodes('&',mensaje);
    }

    // Una función para acceder al archivo messages.yml
    public static String getMessage(String path){
        return color(ComandoMensaje.getMessages().getString(path));
    }

    public static void susurrar(Player emisor,Player receptor,String mensaje){
        if(ComandoMensaje.getInstance().getConfig().getBoolean("see-your-own-msg")){
            String paraMandar = funciones.color("&7"+funciones.getMessage("you-whispered-to")+receptor.getName()+":"+mensaje);
            if(ComandoMensaje.getInstance().getConfig().getBoolean("dot-end-whisper")){
                paraMandar = paraMandar.concat(".");
            }
            // Mensaje para el que ejecuta el comando /mensaje <Jugador> "mensaje enviado"
            emisor.sendMessage(paraMandar);
        }
        String paraRecivir = funciones.color("&7"+emisor.getName()+"te susurra:"+mensaje);
        if(ComandoMensaje.getInstance().getConfig().getBoolean("dot-end-whisper")){
            paraRecivir = paraRecivir.concat(".");
        }

        // Mensaje para el jugador dentro de args[0]; es decir, el receptor del mensaje
        receptor.sendMessage(paraRecivir);

        if(ComandoMensaje.getInstance().getConfig().getBoolean("sound-enabled")){
            String soundS = ComandoMensaje.getInstance().getConfig().getString("choose-sound");
            Sound sound = Sound.valueOf(soundS);
            receptor.playSound(receptor.getLocation(),sound,1f,1f);
        }

        // Guardo la información para el comando /responder (Hecha de manera eficiente para que cada receptor tenga únicamente un "Player" al que responder, no varios)
        if(ComandoMensaje.getInstance().infoResponder.containsKey(receptor.getUniqueId().toString())){
            if(!ComandoMensaje.getInstance().infoResponder.containsValue(emisor.getUniqueId().toString())){
                ComandoMensaje.getInstance().infoResponder.replace(receptor.getUniqueId().toString(),ComandoMensaje.getInstance().infoResponder.get(receptor.getUniqueId().toString()),emisor.getUniqueId().toString());
            }
        }else{
            ComandoMensaje.getInstance().infoResponder.put(receptor.getUniqueId().toString(),emisor.getUniqueId().toString());
        }
    }
 }
