package muriplz.lel.comandos;

import muriplz.lel.ComandoMensaje;
import muriplz.lel.funciones;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mensaje implements CommandExecutor {

    private final ComandoMensaje plugin;

    public Mensaje(ComandoMensaje plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.name+"No puedes usar este comando desde la consola.");
            return false;
        }else{
            Player player = (Player) sender;
            if(args.length==1){
                player.sendMessage("Tienes que escribir un mensaje.");
            }else if(args.length>1){
                Player player2 = Bukkit.getPlayer(args[0]);
                if(player2==null){
                    player.sendMessage(funciones.color("&cNo se han encontrado jugadores."));
                    return false;
                }
                if(player==player2){
                    player.sendMessage("No te puedes mandar mensajes a ti mismo.");
                    return false;
                }
                String mensaje = " ";
                for(int i=1;i<=args.length-1;i++){
                    mensaje = mensaje.concat(args[i]);
                }
                player.sendMessage(funciones.color("&7Le has susurrado a "+player2.getName()+":"+mensaje));
                player2.sendMessage(funciones.color("&7"+player.getName()+" te susurra:"+mensaje));
            }else{
                player.sendMessage("Usa /mensaje <Jugador>");
            }
        }

    return true;}
}

