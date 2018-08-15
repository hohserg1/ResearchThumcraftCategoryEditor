package hohserg.advancedauromancy

import hohserg.advancedauromancy.client.ClientEventHandler
import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.api.research.ResearchCategories

@Mod(name="ResearchThumcraftCategoryEditor",modid = Main.researchThumcraftCategoryEditorModId, version="1.0",modLanguage = "scala",dependencies = "required-after:thaumcraft")
object Main {
  @SidedProxy(clientSide = "hohserg.advancedauromancy.ClientProxy",serverSide = "hohserg.advancedauromancy.ServerProxy")
  var proxy:CommonProxy = _

  final val researchThumcraftCategoryEditorModId="ResearchThumcraftCategoryEditor".toLowerCase

  @Mod.EventHandler def preinit(event: FMLPreInitializationEvent): Unit = {
    proxy.preinit(event)
    NetworkRegistry.INSTANCE.registerGuiHandler(Main, proxy)
    RodsAndCaps.initRodAndCaps()
  }

  @Mod.EventHandler def init(event: FMLInitializationEvent): Unit = {
    proxy.init(event)

  }

  @Mod.EventHandler def postinit(event: FMLPostInitializationEvent): Unit = {
    proxy.postinit(event)
  }
}
class ServerProxy extends CommonProxy{

}

class ClientProxy{
  override def preinit(event: FMLPreInitializationEvent): Unit = {
    super.preinit(event)
    MinecraftForge.EVENT_BUS.register(new ClientEventHandler)
  }
}

class CommonProxy extends IGuiHandler{
  def preinit(event: FMLPreInitializationEvent): Unit = {
    event.getModMetadata.autogenerated = false
    event.getModMetadata.authorList add "hohserg"
  }

  def init(event: FMLInitializationEvent): Unit = {
  }

  def postinit(event: FMLPostInitializationEvent): Unit = {
    ResearchCategories.registerCategory(researchThumcraftCategoryEditorModId.toUpperCase,"FLUX",
      new AspectList().add(Aspect.AURA,1).add(Aspect.CRAFT,1).add(Aspect.MAGIC,1),
      new ResourceLocation(researchThumcraftCategoryEditorModId,"textures/icon.png"),
      new ResourceLocation(researchThumcraftCategoryEditorModId,"textures/background.png")
    )
    ThaumcraftApi.registerResearchLocation(new ResourceLocation(Main.researchThumcraftCategoryEditorModId, "research/research.json"))
  }
}