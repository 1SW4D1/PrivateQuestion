package kr.foundcake.private_question.command

enum class Commands(val cmdName: String, val description: String) {
	CHANNEL_SETUP("채널설정", "비공개 질문이 올라갈 채널을 설정합니다."),
	CHECK_QUESTIONER("질문자확인", "비공개 질문자의 정보를 확인합니다."),
	PRIVATE_QUESTION("비공개질문", "비공개로 질문 할 수 있습니다.")
}