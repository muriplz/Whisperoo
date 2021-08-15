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

            UUID uuid = UUID.fromString(infoRes.get(player.getUniqueId().toString()));

            Player player2 = Bukkit.getPlayer(uuid);

            if(player2 == null){
                player.sendMessage(funciones.getMessage("player-not-found"));
                return false;
            }
            String mensaje = "";
            for(int i = 0 ; i <= args.length-1 ; i++){
                mensaje = mensaje.concat(" "+args[i]);
            }

            // Se mandan los respectivos mensajes
            funciones.susurrar(player,player2,mensaje);

            // La información del HashMap se borrará si esta activada la opción en config.yml
            if(ComandoMensaje.getInstance().getConfig().getBoolean("delete-respond-info")){
                infoRes.remove(player.getUniqueId().toString(),player2.getUniqueId().toString());
            }

        }
        return true;
    }
}
