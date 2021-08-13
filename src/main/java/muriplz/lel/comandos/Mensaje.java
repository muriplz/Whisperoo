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

            // Si el emisor solo ha ejecutado /mensaje <Player/Cualquier cosa>, pero no un mensaje, el comando deja de ejecutarse
            if(args.length==1){
                player.sendMessage("Tienes que escribir un mensaje.");

            // Si el emisor ha ejecutado el comando con algún mensaje entra dentro de este "else if"
            }else if(args.length>1){

                // Inicializamos el objeto "Player" del receptor del mensaje
                Player player2 = Bukkit.getPlayer(args[0]);

                // Si el receptor del mensaje no es un jugador o no está conectado, el comando deja de ejecutarse
                if(player2==null){
                    player.sendMessage(funciones.color("&cNo se han encontrado jugadores."));
                    return false;
                }

                // Cuando el jugador se intenta mandar cualquier mensaje a sí mismo, el comando deja de ejecutarse
                if(player==player2){
                    player.sendMessage("No te puedes mandar mensajes a ti mismo.");
                    return false;
                }

                // Construcción de la frase que quieres susurrar (Mediante una concatenación)
                String mensaje = " ";
                for(int i=1;i<=args.length-1;i++){
                    mensaje = mensaje.concat(args[i]);
                }

                // Mensaje para el que ejecuta el comando /mensaje <Jugador> "mensaje enviado"
                player.sendMessage(funciones.color("&7Le has susurrado a "+player2.getName()+":"+mensaje));

                // Mensaje para el jugador dentro de args[0]; es decir, el receptor del mensaje
                player2.sendMessage(funciones.color("&7"+player.getName()+" te susurra:"+mensaje));
                if(funciones.tieneSonidoAlRecibirMensaje()){
                    String soundS = ComandoMensaje.getInstance().getConfig().getString("elige-el-sonido");
                    Sound sound = Sound.valueOf(soundS);
                    player2.playSound(player.getLocation(),sound,1f,1f);
                }

                // TODO: Hacer el comando /responder, todavía está en modo comentario (desactivado)
                // Guardo la información para el comando /responder (Hecha de manera eficiente para que cada receptor tenga únicamente un "Player" al que responder, no varios)
       //         if(plugin.infoResponder.containsKey(player2.getUniqueId().toString())){
       //             if(!plugin.infoResponder.containsValue(player.getUniqueId().toString())){
       //                 plugin.infoResponder.replace(player2.getUniqueId().toString(),plugin.infoResponder.get(player2.getUniqueId().toString()),player.getUniqueId().toString());
       //             }
       //         }else{
       //             plugin.infoResponder.put(player2.getUniqueId().toString(),player.getUniqueId().toString());
       //         }

            // Cuando usa únicamente /mensaje, lo cual no debería de hacer nada, así que se manda un mensaje con información de como usar el comando (args.length sería igual a 0 en este caso)
            }else{
                player.sendMessage("Usa /mensaje <Jugador>");
            }
        }

    return true;}
}

