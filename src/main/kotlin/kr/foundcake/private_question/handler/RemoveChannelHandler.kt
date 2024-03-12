package kr.foundcake.private_question.handler

import dev.minn.jda.ktx.events.listener
import kr.foundcake.private_question.database.DBManager
import kr.foundcake.private_question.dto.ServerSetting
import kr.foundcake.private_question.dto.Writer
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent

fun removeChannelHandler(jda: JDA) {
	jda.listener<ChannelDeleteEvent> {
		when(it.channel.type) {
			ChannelType.FORUM -> {
				val setting: ServerSetting? = DBManager.SeverSettingRepo.find(it.guild.idLong)
				if(setting !== null && setting.channel == it.channel.idLong) {
					DBManager.SeverSettingRepo.remove(setting)
				}
			}
			ChannelType.GUILD_PUBLIC_THREAD -> {
				val writer: Writer? = DBManager.WriterRepo.find(it.channel.idLong)
				if(writer !== null) {
					DBManager.WriterRepo.remove(writer)
				}
			}
			else -> Unit
		}
	}
}