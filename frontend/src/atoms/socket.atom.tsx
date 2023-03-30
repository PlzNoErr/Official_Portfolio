import { ReactElement } from "react";
import { atom, selector, SetRecoilState, useRecoilCallback } from "recoil";
import { v4 as uuidv4 } from "uuid";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { Chat, chatListState } from "./chat.atom";

interface BaseResponse {
  type: string;
  operation: string;
  data: object;
}

const SOCKET_BASE_URL = "https://musicat.kr/api/ws";

let stompClient: Stomp.Client | null = null;

// 소켓 연결
export const socketConnection = () => {
  const callback = useRecoilCallback(
    ({ set }) =>
      async () => {
        if (stompClient) return;

        const socket = new SockJS(SOCKET_BASE_URL);
        stompClient = Stomp.over(socket);

        // 스톰프 디버그 모드 끄기 ( 콘솔창에 뜨는거)
        stompClient.debug = () => {
          return null;
        };

        stompClient.connect({}, () => {
          stompClient?.subscribe("/topic", (message) => {
            dataClassification(set, JSON.parse(message.body));
          });
          stompClient?.subscribe("/user/queue", (message) => {
            dataClassification(set, JSON.parse(message.body));
          });

          stompClient?.send("/app/subscribe", {}, "");
        });
      },
    []
  );

  return callback;
};

// 데이터 전송
export const sendData = async (api: string, data: object) => {
  if (stompClient && stompClient.connected) {
    stompClient.send(api, {}, JSON.stringify(data));
  } else {
    console.error("소켓이 연결되지 않았습니다.");
  }
};


// 데이터 분류
const dataClassification = (set: any, res: BaseResponse):void => {
  switch (res.type) {
    case "CHAT":

      console.log("나는 챗")
      set(chatListState,(prev:Chat[]) => [...prev, res.data])
      break;
    case "RADIO":
      console.log("나는 라디오")
      console.log(res.data)
      break;
    default:
      break;
  }
};