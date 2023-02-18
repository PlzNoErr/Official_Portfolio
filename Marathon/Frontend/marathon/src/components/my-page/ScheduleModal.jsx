import style from "./ScheduleModal.module.css";
import Swal from "sweetalert2";
import { useSelector } from "react-redux";
import { useState, useEffect } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { useMutation } from "@tanstack/react-query";
import { $ } from "util/axios";
import { v4 as uuidv4 } from "uuid";

export default function ScheduleModal({ modalData, setIsModalOpen }) {
  const state = useSelector((state) => state);
  const queryClient = useQueryClient();

  const [isCreatable, setIsCreatable] = useState(true);

  // 모바일일때 돌아가게 만들기
  const isMobile = () => {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
      navigator.userAgent
    );
  };

  const { mutate } = useMutation(
    () => $.delete(`/doctor-treatment/${modalData.reservedDay.treatmentSeq}`),
    {
      onMutate: async () => {
        await queryClient.cancelQueries(["mypageSchedule"]);
        const oldData = queryClient.getQueryData(["mypageSchedule"]);
        queryClient.setQueryData(["mypageSchedule"], updateData());
        return { oldData };
      },
      onError: (_error, _variables, context) => {
        queryClient.setQueryData(["mypageSchedule"], context.oldData);
      },
      onSettled: () => {
        queryClient.invalidateQueries(["mypageSchedule"]);
      },
    }
  );

  const updateData = () => {
    let newData = queryClient.getQueryData(["mypageSchedule"]);
    for (let i = 0; i < newData.data.list.length; i++) {
      if (
        newData.data.list[i].treatmentSeq === modalData.reservedDay.treatmentSeq
      ) {
        let temp = [...newData.data.list];
        temp.splice(i, 1);
        newData.data.list = temp;
        break;
      }
    }
    return newData;
  };

  useEffect(() => {
    let date = new Date(modalData.reservedDay.dateTime).getTime();
    let nowTime = new Date();
    if (
      -3600000 <= date - nowTime.getTime() &&
      date - nowTime.getTime() <= 600000
    ) {
      setIsCreatable(true);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className={style.modal_container}>
      <div className={style.modal_left_box}>
        <img
          className={style.modal_profile}
          src={
            modalData.reservedDay.doctorImg || modalData.reservedDay.patientImg
          }
          alt=""
        />
      </div>
      <div className={style.modal_right_box}>
        <h3 style={{ display: "inline-block" }}>
          {modalData.reservedDay.doctorName ||
            modalData.reservedDay.patientName}
        </h3>
        <h4>{state.loginUser.userRole === "patient" ? " 선생님 " : " 님 "}</h4>
        <hr />
        <span style={{ color: "gray", marginRight: "10px" }}>예약 구분</span>
        <span>재활</span>
        <br />
        <span
          style={{
            display: "inline-block",
            marginTop: "15px",
            color: "gray",
            marginRight: "10px",
          }}
        >
          예약 날짜
        </span>
        <span>
          {new Date(modalData.reservedDay.dateTime).toLocaleDateString()}
        </span>
        <span>({modalData.dayOfWeek})</span>
        <span>{new Date(modalData.reservedDay.dateTime).getHours()}시</span>
        {state.loginUser.userRole === "doctor" ? (
          <>
            <div style={{ display: "flex" }}>
              <div style={{ flexGrow: "1" }}></div>
              <div>
                <button
                  className={
                    isCreatable
                      ? style.button + " " + style.button_create
                      : style.button +
                        " " +
                        style.button_create +
                        " " +
                        style.unable
                  }
                  style={{ marginRight: "10px" }}
                  onClick={() => {
                    if (isMobile()) {
                      Swal.fire({
                        icon: "error",
                        title: "",
                        text: "모바일에서는 지원하지 않는 기능입니다. 빠르게 기능을 업데이트 하도록 하겠습니다!",
                        confirmButtonText: "닫기",
                      });
                    } else {
                      let sessionId =
                        new Date().getTime() + "marathon" + uuidv4();

                      $.post("/doctor-treatment/alarm", {
                        treatmentSeq: modalData.reservedDay.treatmentSeq,
                        sessionId: sessionId,
                      }).then((data) => {
                        //로컬 스토리지에 저장
                        localStorage.setItem("historySeq", data.data);
                        console.log("이거는 히스토리여");
                        console.log(localStorage.getItem("historySeq"));
                        window.open(
                          `/treat/${sessionId}`,
                          `Marathon - 화상제활`,
                          "fullscreen, menubar=no, status=no, toolbar=no, location=no"
                        );
                      });
                      setIsModalOpen(false);
                    }
                  }}
                >
                  방 생성
                </button>
                <button
                  className={style.button + " " + style.button_cancel}
                  onClick={() => {
                    Swal.fire({
                      icon: "warning",
                      title: "",
                      text: "정말로 취소하시겠습니까?",

                      showCancelButton: true,
                      confirmButtonColor: "#3085d6",
                      cancelButtonColor: "#d33",
                      confirmButtonText: "확인",
                      cancelButtonText: "취소",
                    }).then((result) => {
                      if (result.isConfirmed) {
                        Swal.fire({
                          icon: "success",
                          text: "취소가 완료되었습니다.",
                        });
                        mutate();
                        setIsModalOpen(false);
                      }
                    });
                  }}
                >
                  예약 취소
                </button>
              </div>
            </div>
            <p>
              ※ 수업시작 10분전, 수업 예약 시간 1시간 후 까지만 방생성이
              활성화됩니다.
            </p>
          </>
        ) : null}
      </div>
    </div>
  );
}
