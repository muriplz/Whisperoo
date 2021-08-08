package muriplz.lel;

import org.bukkit.ChatColor;

public class funciones {

    // Una función que utiliza el sistema hexadecimal para usar la función ChatColor (https://foro.landacraft.es/t/colabora-economicamente-ayudanos-a-mantener-el-servidor/192) Colores y formato en los carteles > Guía de formato y colores.
    public static String color(String mensaje){
        return ChatColor.translateAlternateColorCodes('&',mensaje);
    }
}
