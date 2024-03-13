package kr.foundcake.private_question.handler

import dev.minn.jda.ktx.events.listener
import kr.foundcake.private_question.database.DBManager
import kr.foundcake.private_question.dto.ServerSetting
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent

fun handleRemoveChannel(jda: JDA) {
	jda.listener<ChannelDeleteEvent> {
		when(it.channel.type) {
			ChannelType.FORUM -> {
				val setting: ServerSetting? = DBManager.ServerSettingRepo.find(it.guild.idLong)
				if(setting !== null && setting.channel == it.channel.idLong) {
					DBManager.ServerSettingRepo.remove(setting)
					DBManager.WriterRepo.removeByServerId(it.guild.idLong)
				}
			}
			ChannelType.GUILD_PUBLIC_THREAD -> {
				DBManager.WriterRepo.remove(it.channel.idLong)
			}
			else -> Unit
		}
	}
}