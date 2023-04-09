# MusiCat

![프로젝트 로고](./image/Logo.png/)
ChatGPT와인공지능 음성기술을 결합한 인공지능 라디오 DJ 

프로젝트 URL : [AI DJ Musicat](https://musicat.kr)

프로젝트 UCC : [Musicat - UCC](https://ramen-buang.notion.site/Musicat-UCC-c561acbeb18743fb94ad90b9eeb6be23)

프로젝트 기간: 2023.02.27 ~ 2023.04.07

## 프로젝트 소개 (배경, 개요)
 많은 기업에서 ChatGPT를 활용한 다양한 서비스를 출시하고 있습니다. 이런 흐름에 발맞춰 인공지능 음성도메인과 결합하여 인공지능 DJ 라디오 방송인 뮤직캣을 기획하게 되었습니다.

 지치지 않는 AI DJ 뮤직캣은 청취자에게 연중 무휴 24시간 중단 없는 서비스를 경험을 제공합니다. 또한 라디오 진행에 필요한 수많은 자원을 대신하여 프로그램 관리만을 통해 방송컨텐츠를 제공할 수 있습니다.

## 프로젝트 기능 소개
- 채팅 : 다른 청취자들과 소통이 가능하고, 라디오 DJ가 댓글을 읽고 반응하여 청취자와 신선하고 재미있는 상호작용을 합니다.
- 사연 : 하나의 목소리가 아닌, 다양한 목소리를 통해 사연을 읽어줘서 더욱 다채로운 사연 낭독을 합니다. 
- 신청곡 : 원하는 노래를 신청하고, 스트리밍하여 다른 청취자들에게 자신의 최애 노래를 뽑낼 수 있습니다. 
- 마이페이지 : 사이트 내에서 사용되는 포인트의 거래내역, 알림, 공지사항이 확인 가능하고 뱃지, 배경, 테마를 커스터마이징하여 자신만의 라디오를 만들 수 있습니다. 

## 사용 기술
* 이슈 관리 : Jira
* 형상 관리 : Git, Gitlab
* 의사소통, 협업: Notion, Mattermost, Discord
* 개발환경
    * OS : Window10
    * IDE : Intellij, VSCode
    * EC2 : Ubuntu 20.04 LTS (GNU/Linux 5.4.0-1018-aws x86_64)
    * Database : Mariadb 10.6
    * SSH : Windows Terminal, MobaXterm
    * CI/CD : Jenkins
    * Reverse Proxy : Nginx
    * SSL : CertBot, Let's Encrypt
* 프론트엔드 (React)
    * Typescript
    * React
    * Recoil
    * React-Query
    * Vite
    * sockjs-Client
    * stompjs
    * threejs
* 백엔드 (SpringBoot)
    * Springboot Starter Data JPA
    * Springboot Starter Websocket
    * Springboot Starter Security
    * JWT
    * Spring kafka
    * google api services youtube v3 (youtube data api v3)
    * google http client gson
    * jsoup
    * lombok
    * spring boot devtools
    * mariadb java client
* 백엔드 (RadioServer)
    * FastAPI
    * asyncio
    * pydub
    * mariadb
    * kafka
    * gunicorn
    * uvicorn

## 프로젝트 파일 구조
### 백엔드
```bash
.
├── gradle
│   └── wrapper
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── musicat
    │   │           ├── Oauth
    │   │           ├── auth
    │   │           ├── config
    │   │           ├── controller
    │   │           │   ├── item
    │   │           │   ├── notice
    │   │           │   ├── radio
    │   │           │   └── user
    │   │           ├── data
    │   │           │   ├── dto
    │   │           │   │   ├── alert
    │   │           │   │   │   ├── request
    │   │           │   │   │   └── response
    │   │           │   │   ├── chat
    │   │           │   │   ├── item
    │   │           │   │   ├── music
    │   │           │   │   ├── notice
    │   │           │   │   ├── radio
    │   │           │   │   ├── socket
    │   │           │   │   ├── spotify
    │   │           │   │   ├── story
    │   │           │   │   └── user
    │   │           │   ├── entity
    │   │           │   │   ├── item
    │   │           │   │   ├── notice
    │   │           │   │   ├── radio
    │   │           │   │   └── user
    │   │           │   └── repository
    │   │           │       ├── item
    │   │           │       ├── notice
    │   │           │       ├── radio
    │   │           │       └── user
    │   │           ├── handler
    │   │           ├── interceptor
    │   │           ├── jwt
    │   │           ├── service
    │   │           │   ├── chat
    │   │           │   ├── item
    │   │           │   ├── kafka
    │   │           │   ├── notice
    │   │           │   ├── radio
    │   │           │   ├── socket
    │   │           │   └── user
    │   │           └── util
    │   │               └── builder
    │   └── resources
    │       └── static
    └── test
        └── java
            └── com
                └── musicat
```

### 프론트엔드
```bash
.
├── public
│   ├── graphic
│   │   ├── animation
│   │   ├── background
│   │   │   ├── 1
│   │   │   └── 2
│   │   └── cat
│   └── img
│       ├── background
│       ├── badge
│       ├── cd
│       ├── pagebackground
│       ├── tape
│       └── theme
└── src
    ├── asset
    │   ├── font
    │   └── img
    ├── atoms
    ├── components
    │   ├── broadcast
    │   │   ├── graphicCanvas
    │   │   │   └── background
    │   │   └── radioPlayer
    │   ├── common
    │   │   ├── board
    │   │   ├── button
    │   │   ├── input
    │   │   ├── modal
    │   │   ├── pagenation
    │   │   ├── selectBox
    │   │   └── songSearch
    │   ├── header
    │   │   ├── onairSign
    │   │   └── popover
    │   └── sideNav
    │       ├── mypageNav
    │       └── tapeNav
    │           ├── CDplayer
    │           ├── Tape
    │           ├── tapeButtons
    │           └── volumeBar
    ├── connect
    │   ├── axios
    │   │   └── queryHooks
    │   └── socket
    ├── customHooks
    ├── pages
    │   ├── common
    │   │   ├── loadingSpinner
    │   │   ├── loginSuccess
    │   │   └── page404
    │   ├── home
    │   │   ├── about
    │   │   ├── chat
    │   │   ├── songRequest
    │   │   │   └── songList
    │   │   │       └── songDetailModal
    │   │   └── story
    │   │       ├── contentBox
    │   │       └── contentPlus
    │   └── mypage
    │       ├── inventory
    │       │   └── inventoryModal
    │       ├── myinfo
    │       │   └── myinfoModal
    │       ├── notice
    │       ├── noticeDetail
    │       ├── noticeManage
    │       ├── noticeManageModify
    │       └── userManage
    │           └── SelectedUsers
    └── types
```
### 라디오 서버
```bash
.
├── api_chatgpt.py
├── api_naver_tts.py
├── database.py
├── kafka_handler.py
├── logic_chat.py
├── logic_empty.py
├── logic_music.py
├── logic_opening.py
├── logic_story.py
├── main.py
├── my_logger.py
├── my_util.py
├── radio_progress.py
├── shared_env.py
├── shared_state.py
└── tts
    └── mymusic
```

## 역할 분배
| 이름 | 사진 | 역할 | 정보 |
| ---- | ---- | ---- | ---- |
| 김동언 | ![김동언](./image/human1.png) | 백엔드 | 카프카 / FastApi / CI/CD |
| 이찬희 | ![이찬희](./image/human2.png) | 백엔드 | SpringBoot |
| 최웅렬 | ![최웅렬](./image/human4.png) | 백엔드 | SpringBoot / Oath / Security |
| 이연학 | ![이연학](./image/human6.png) | 프론트엔드 | React |
| 박동환 | ![박동환](./image/human3.png) | 프론트엔드 | React |
| 최다은 | ![최다은](./image/human5.png) | 프론트엔드 | React |

## 프로젝트 산출물

### 기능 기획서
  ![기능 기획서](./image/work1.png)
  ![기능 기획서](./image/work2.png)

  ---

### 시스템 아키택쳐
  ![아키텍쳐](./image/system.png)

  ---

### API 명세서
  ![API 명세서](./image/api1.png)
  ![API 명세서](./image/api2.png)
  ![API 명세서](./image/api3.png)
  ![API 명세서](./image/api4.png)
  ![API 명세서](./image/api5.png)
  ![API 명세서](./image/api6.png)
  ![API 명세서](./image/api7.png)
  ![API 명세서](./image/api8.png)

  ---

### ERD 다이어그램
  ![ERD](./image/ERD.png)

  ---

### MockUp & Design
  ![mockup](./image/mockup1.png)
  ![mockup](./image/mockup2.png)
  ![mockup](./image/mockup3.png)

  ---

## 서비스 동작 이미지와 설명

### 메인페이지
![메인페이지](./image/메인페이지.gif)  
전체적인 화면을 볼 수 있는 메인페이지
### 사연신청
![사연신청](./image/사연신청.gif)  
사연을 신청할 수 있는 페이지. 사연을 읽기를 원하는 화자를 선택해서 신청이 가능하다.  
### 노래신청
![노래신청](./image/노래신청.gif)  
내가 듣고 싶은 노래를 신청할 수 있는 페이지
### 채팅
![채팅](./image/채팅.gif)  
페이지를 사용하는 사용자, DJ와 소통 할 수 있는 페이지. 현재 상태가 소통중이면 DJ가 채팅중을 읽고 반응을 한다.

## 마이페이지

### 나의 정보 관리
![마이페이지](./image/마이페이지.gif)  
내 전체적인 정보를 알 수 있는 페이지. 다크모드, 닉네임 변경, 나의 츄르 변동내역을 확인 가능하다.
### 알림 / 공지사항
![공지사항](./image/공지사항.gif)  
공지사항과 개인 알림을 받을 수 있는 페이지
### 인벤토리
![인벤토리](./image/인벤토리.gif)  
내 화면에 있는 구성 요소들을 바꿀 수 있는 페이지. 배지, 배경, 테마를 츄르를 소모햐여 변경가능하다.
### 관리자페이지
![관리자페이지](./image/관리자페이지.gif)  
다른 사용자들의 상태를 관리하는 페이지. 관리자만 들어갈 수 있다. 사용자들의 채팅, 정지 여부 상태를 변경 가능하다.



## 프로젝트 참고 링크

- [프로젝트 Notino](https://ramen-buang.notion.site/SSAFY-2-MusiCat-6ce1496529df4689bdae266db3d50466) Musicat
- [GitHub - Link](자기 githut 링크) 포트폴리오 Github Link


