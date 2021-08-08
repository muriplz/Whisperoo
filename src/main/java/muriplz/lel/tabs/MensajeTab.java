package muriplz.lel.tabs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MensajeTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length==1){
            // completions is the returned Lists, starts empty
            List<String> completions = new ArrayList<>();

            // Initialize allTabs
            List<String> allTabs = new ArrayList<>();

            // Get the name of all online players and add it to allTabs
            Bukkit.getOnlinePlayers().forEach(p -> allTabs.add(p.getName()));

            // Add to "completions" all words that have letters that are contained on "commands" list
            int i=0;
            while(i < allTabs.size()){
                if(allTabs.get(i).toLowerCase().startsWith(args[0].toLowerCase())){
                    completions.add(allTabs.get(i));
                }
                i++;
            }
            return completions;
        }
        return new ArrayList<>();
    }
}
