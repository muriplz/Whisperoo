package muriplz.lel.comandos;

import muriplz.lel.ComandoMensaje;
import muriplz.lel.funciones;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Mensaje implements CommandExecutor {

    private final ComandoMensaje plugin;

    public Mensaje(ComandoMensaje plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.name+"No puedes usar este comando desde la consola.");
            return false;
        }else{

            // Inicializamos el objeto "Player" del emisor del mensaje
            Player player = (Player) sender;

            if(!player.hasPermission("mensaje.mensaje")){
                player.sendMessage(funciones.getMessage("no-permission"));
                return false;
            }

            // Cuando usa únicamente /mensaje, lo cual no debería de hacer nada, así que se manda un mensaje con información de como usar el comando (args.length sería igual a 0 en este caso)
            if(args.length==0){
                player.sendMessage(funciones.getMessage("msg-usage"));
                return false;
            }

            Player player2 = Bukkit.getPlayer(args[0]);

            // Si el receptor del mensaje no es un jugador o no está conectado, el comando deja de ejecutarse
            if(player2==null){
                player.sendMessage(funciones.getMessage("player-not-found"));
                return false;
            }
            // Si el emisor solo ha ejecutado /mensaje <Player/Cualquier cosa>, pero no un mensaje, el comando deja de ejecutarse
            else if(args.length==1){
                player.sendMessage(funciones.getMessage("no-message-written"));

            // Si el emisor ha ejecutado el comando con algún mensaje entra dentro de este "else if"
            }else {

                boolean autoMensajes= ComandoMensaje.getInstance().getConfig().getBoolean("msg-yourself");

                // Cuando el jugador se intenta mandar cualquier mensaje a sí mismo, el comando deja de ejecutarse
                if(player==player2&&!autoMensajes){
                    player.sendMessage(funciones.getMessage("cant-msg-yoursef"));
                    return false;
                }

                // Construcción de la frase que quieres susurrar (Mediante una concatenación)
                String mensaje = "";
                for(int i=1;i<=args.length-1;i++){
                    mensaje = mensaje.concat(" "+args[i]);
                }

                if(ComandoMensaje.getInstance().getConfig().getBoolean("see-your-own-msg")){
                    String paraMandar = funciones.color("&7"+funciones.getMessage("you-whispered-to")+player2.getName()+":"+mensaje);
                    if(ComandoMensaje.getInstance().getConfig().getBoolean("dot-end-whisper")){
                        paraMandar = paraMandar.concat(".");
                    }
                    // Mensaje para el que ejecuta el comando /mensaje <Jugador> "mensaje enviado"
                    player.sendMessage(paraMandar);
                }
                String paraRecivir = funciones.color("&7"+player.getName()+"te susurra:"+mensaje);
                if(ComandoMensaje.getInstance().getConfig().getBoolean("dot-end-whisper")){
                    paraRecivir = paraRecivir.concat(".");
                }


                // Mensaje para el jugador dentro de args[0]; es decir, el receptor del mensaje
                player2.sendMessage(paraRecivir);

                if(ComandoMensaje.getInstance().getConfig().getBoolean("sound-enabled")){
                    String soundS = ComandoMensaje.getInstance().getConfig().getString("choose-sound");
                    Sound sound = Sound.valueOf(soundS);
                    player2.playSound(player.getLocation(),sound,1f,1f);
                }

                // Guardo la información para el comando /responder (Hecha de manera eficiente para que cada receptor tenga únicamente un "Player" al que responder, no varios)
                if(plugin.infoResponder.containsKey(player2.getUniqueId().toString())){
                    if(!plugin.infoResponder.containsValue(player.getUniqueId().toString())){
                        plugin.infoResponder.replace(player2.getUniqueId().toString(),plugin.infoResponder.get(player2.getUniqueId().toString()),player.getUniqueId().toString());
                    }
                }else{
                    plugin.infoResponder.put(player2.getUniqueId().toString(),player.getUniqueId().toString());
                }

            }
        }

    return true;}
}

