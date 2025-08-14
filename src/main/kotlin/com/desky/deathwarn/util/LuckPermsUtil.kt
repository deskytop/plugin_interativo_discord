import net.luckperms.api.node.Node
import net.luckperms.api.node.types.InheritanceNode
import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.entity.Player

object LuckPermsUtil {
    private val luckPerms: LuckPerms = LuckPermsProvider.get()

    fun isPlayerInPlanetaGroup(player: Player): Boolean {
        val user = luckPerms.userManager.getUser(player.uniqueId) ?: return false
        val nodes: Collection<Node> = user.data().toCollection()

        return nodes.any { node ->
            node is InheritanceNode && node.groupName.equals("planeta", ignoreCase = true)
        }
    }
}