# PrivateQuestion

질문의 부담을 줄이기 위한 질문을 대신 올려주는 디스코드 봇입니다.

## Setting
1. 최신 Release에서 `PrivateQuestion.jar`파일을 받는다.
2. 같은 경로에 `.env` 파일을 만들고 아래 예시와 같이 입력한다.
    ```bash
    TOKEN=YOUR_DISCORD_TOKEN
    DB_PASSWORD=YOUR_DB_PASSWORD
    DB_NAME=YOUR_DB_NAME
    ```
3. docker-compose.prod.yml 파일을 실행시킨다.

## Command
|   명령어   |    필요권한    |
|:-------:|:----------:|
|  채널설정   |   서버관리하기   |
|  질문자확인  |  스레드 관리하기  |
|  비공개질문  |     -      |