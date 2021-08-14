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

import java.util.HashMap;
import java.util.UUID;

public class Responder implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ComandoMensaje.getInstance().name+"No puedes usar este comando desde la consola.");
            return false;
        }else{

            Player player = (Player) sender;

            if(!player.hasPermission("mensaje.responder")){
                player.sendMessage(funciones.getMessage("no-permission"));
                return false;
            }

            if(args.length==0){
                player.sendMessage(funciones.getMessage("no-message-written"));
                return false;
            }

            HashMap<String,String> infoRes = ComandoMensaje.getInstance().infoResponder;
            if(!infoRes.containsKey(player.getUniqueId().toString())){
                player.sendMessage(funciones.getMessage("none-to-respond"));
                return false;
            }

            String jugadorParaResponder = infoRes.get(player.getUniqueId().toString());

            UUID uuid = UUID.fromString(jugadorParaResponder);

            Player player2 = Bukkit.getPlayer(uuid);

            if(player2 == null){
                player.sendMessage(funciones.getMessage("player-not-found"));
                return false;
            }
            String message = "";
            for(int i = 0 ; i <= args.length-1 ; i++){
                message = message.concat(" "+args[i]);
            }

            if(ComandoMensaje.getInstance().getConfig().getBoolean("see-your-own-msg")){
                String paraMandar = funciones.color("&7"+funciones.getMessage("you-whispered-to")+player2.getName()+":"+message);
                if(ComandoMensaje.getInstance().getConfig().getBoolean("dot-end-whisper")){
                    paraMandar = paraMandar.concat(".");
                }
                // Mensaje para el que ejecuta el comando /mensaje <Jugador> "mensaje enviado"
                player.sendMessage(paraMandar);
            }

            String paraRecivir = funciones.color("&7"+player.getName()+funciones.getMessage("someone-whispered-you")+message);
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
            if(ComandoMensaje.getInstance().getConfig().getBoolean("delete-respond-info")){
                infoRes.remove(player.getUniqueId().toString(),player2.getUniqueId().toString());
            }


        }
        return true;
    }
}
