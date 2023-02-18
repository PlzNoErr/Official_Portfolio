import React, { useState } from "react";
import { useQuery, useMutation } from "@tanstack/react-query";
import Swal from "sweetalert2";
import { $ } from "util/axios";
import style from "./SelectSchedule.module.css";

export default function SelectSchedule({ name, seq }) {
  const [cnt, setCnt] = useState(0);
  const [teacherSchedule, setTeacherSchedule] = useState();
  const [targetDate, setTargetDate] = useState([]);
  const [target, setTarget] = useState();
  const newData = {
    list: teacherSchedule,
  };

  /** API GET 함수 */
  const { isLoading, data, isError, error } = useQuery(["Time-table"], () =>
    $.get(`/patient-treatment/book/${seq}`)
  );

  /** API PUT 함수 */
  const res_put = () => {
    return $.put(`/patient-treatment/book/${seq}`, newData);
  };

  const { mutate: onPut } = useMutation(res_put, {
    onSuccess: () => {
      Swal.fire({
        icon: "success",
        title: "",
        text: "예약 되었습니다.",
        confirmButtonText: "닫기",
      });
    },
    onError: () => {
      Swal.fire({
        icon: "error",
        title: "",
        text: "실패했습니다.",
        confirmButtonText: "닫기",
      });
    },
  });

  /** 날짜 구하기
   * num = 0 : 월요일 ~ num = 6 : 일요일
   */
  const totalThisDate = (num) => {
    let today = new Date(
      Number(data.data.firstDateInfo) + (num + cnt * 7) * 86400000
    );
    let todayYear = today.getFullYear();
    let todayMonth = today.getMonth() + 1;
    if (todayMonth < 10) todayMonth = "0" + todayMonth;
    let todayDate = today.getDate();
    if (todayDate < 10) todayDate = "0" + todayDate;
    today = todayYear + "-" + todayMonth + "-" + todayDate;
    return today.toString();
  };

  const thisYear = (num) => {
    let today = new Date(
      Number(data.data.firstDateInfo) + (num + cnt * 7) * 86400000
    );
    let todayYear = today.getFullYear();
    return todayYear.toString();
  };

  const thisMonth = (num) => {
    let today = new Date(
      Number(data.data.firstDateInfo) + (num + cnt * 7) * 86400000
    );
    let todayMonth = today.getMonth() + 1;
    if (todayMonth < 10) todayMonth = "0" + todayMonth;
    return todayMonth.toString();
  };

  const thisDay = (num) => {
    let today = new Date(
      Number(data.data.firstDateInfo) + (num + cnt * 7) * 86400000
    );
    let todayDate = today.getDate();
    if (todayDate < 10) todayDate = "0" + todayDate;
    return todayDate.toString();
  };

  const checkSchedule = (thisDay) => {
    const nowDay = new Date();
    const found = data.data.list.find((e) => e.localDate === thisDay);
    const thisTimeTable = found.bitDate;

    const timeLlist = [];
    for (let i = 0; i < thisTimeTable.length; i++) {
      /** 9시 수업인 경우 09:00로 표기하기 위해 따로 구분
       * 11시 다음 수업이 13시 이므로 11시, 12시도 따로 구분
       * 예약은 최소 당일 1시간 전까지 신청가능하도록 설정*/
      if (i === 0) {
        timeLlist.push(
          <button
            key={i}
            className={
              (new Date(Number(new Date(thisDay)) - 3600000) < nowDay &&
                style.outdated) ||
              (thisTimeTable[i] === "0" && style.button0) ||
              (thisTimeTable[i] === "1" && style.button1) ||
              (thisTimeTable[i] === "2" && style.button2) ||
              (thisTimeTable[i] === "3" && style.button3)
            }
            name={thisDay}
            value={cnt.toString() + i.toString() + thisTimeTable[i]}
            onClick={onChange}
            disabled={
              (new Date(Number(new Date(thisDay)) - 3600000) < nowDay &&
                true) ||
              (thisTimeTable[i] === "0" && true) ||
              (thisTimeTable[i] === "2" && true)
            }
          >
            0{i + 9} : 00
          </button>
        );
      } else if (i === 1 || i === 2) {
        timeLlist.push(
          <button
            key={i}
            className={
              (new Date(Number(new Date(thisDay)) + (i - 1) * 3600000) <
                nowDay &&
                style.outdated) ||
              (thisTimeTable[i] === "0" && style.button0) ||
              (thisTimeTable[i] === "1" && style.button1) ||
              (thisTimeTable[i] === "2" && style.button2) ||
              (thisTimeTable[i] === "3" && style.button3)
            }
            name={thisDay}
            value={cnt.toString() + i.toString() + thisTimeTable[i]}
            onClick={onChange}
            disabled={
              (new Date(Number(new Date(thisDay)) + (i - 1) * 3600000) <
                nowDay &&
                true) ||
              (thisTimeTable[i] === "0" && true) ||
              (thisTimeTable[i] === "2" && true)
            }
          >
            {i + 9} : 00
          </button>
        );
      } else {
        timeLlist.push(
          <button
            key={i}
            className={
              (new Date(Number(new Date(thisDay)) + i * 3600000) <= nowDay &&
                style.outdated) ||
              (thisTimeTable[i] === "0" && style.button0) ||
              (thisTimeTable[i] === "1" && style.button1) ||
              (thisTimeTable[i] === "2" && style.button2) ||
              (thisTimeTable[i] === "3" && style.button3)
            }
            name={thisDay}
            value={cnt.toString() + i.toString() + thisTimeTable[i]}
            onClick={onChange}
            disabled={
              (new Date(Number(new Date(thisDay)) + i * 3600000) <= nowDay &&
                true) ||
              (thisTimeTable[i] === "0" && true) ||
              (thisTimeTable[i] === "2" && true)
            }
          >
            {i + 10} : 00
          </button>
        );
      }
    }
    return timeLlist;
  };

  /** 일정 선택 함수 */
  const onChange = (e) => {
    let reserve = "";
    if (e.target.value[2] === "1") {
      reserve = "3";
    } else if (e.target.value[2] === "3") {
      reserve = "1";
    }

    let findItem = data.data.list.find((t) => t.localDate === e.target.name);
    let newTimeTable = [];
    for (let i = 0; i < 8; i++) {
      i.toString() === e.target.value[1]
        ? newTimeTable.push(reserve)
        : newTimeTable.push(findItem.bitDate[i]);
    }
    newTimeTable = newTimeTable.join("");
    let newSchedule = [...data.data.list];
    let findorigin = newSchedule.find((t) => t.localDate === e.target.name);
    findorigin.bitDate = newTimeTable;

    /** 선택 취소된 날짜 반영하기 위한 코드 */
    let prevItem = data.data.list.find((t) => t.localDate === targetDate[0])
      ? data.data.list.find((t) => t.localDate === targetDate[0])
      : null;
    if (prevItem !== null) {
      let newTimeTable2 = [];
      /** 동일한 버튼 클릭시는 아래 코드 동작 X(취소만 가능하도록) */
      if (e.target !== target) {
        for (let i = 0; i < 8; i++) {
          /** 날짜가 다른 경우 */
          if (targetDate[0] !== e.target.name) {
            if (prevItem.bitDate[i] === "3") {
              /** 그외 나머지 */
              newTimeTable2.push("1");
            } else {
              newTimeTable2.push(prevItem.bitDate[i]);
            }
          } else if (targetDate[0] === e.target.name) {
            /** 날짜가 같은 경우 */
            if (prevItem.bitDate[i] === "3") {
              /** 클릭한 버튼을 제외한 나머지에서 값이 3인 부분 1로 변경 */
              if (i.toString() === targetDate[1][1]) {
                newTimeTable2.push("1");
              } else {
                newTimeTable2.push(prevItem.bitDate[i]);
              }
            } else {
              newTimeTable2.push(prevItem.bitDate[i]);
            }
          }
        }
        newTimeTable2 = newTimeTable2.join("");
        let findorigin2 = newSchedule.find(
          (t) => t.localDate === targetDate[0]
        );
        findorigin2.bitDate = newTimeTable2;
      } else {
        if (e.target.value[0] !== targetDate[1][0]) {
          /** 요일, 시간이 같고 주차가 다른 경우 */
          for (let i = 0; i < 8; i++) {
            if (prevItem.bitDate[i] === "3") {
              newTimeTable2.push("1");
            } else {
              newTimeTable2.push(prevItem.bitDate[i]);
            }
          }
          newTimeTable2 = newTimeTable2.join("");
          let findorigin2 = newSchedule.find(
            (t) => t.localDate === targetDate[0]
          );
          findorigin2.bitDate = newTimeTable2;
        }
      }
    }
    setTeacherSchedule(newSchedule);
    setTargetDate([e.target.name, e.target.value]);
    setTarget(e.target);
  };

  /** 다음 일정 확인 */
  const nextWeek = () => {
    if (cnt < 2) {
      setCnt(cnt + 1);
    }
  };

  /** 이전 일정 확인 */
  const prevWeek = () => {
    if (cnt > 0) {
      setCnt(cnt - 1);
    }
  };

  const onSubmit = () => {
    /** 3으로 할당된 부분 2로 수정하기 */
    let findItem = teacherSchedule.find((t) => t.localDate === targetDate[0]);
    let newTimeTable = [];
    for (let i = 0; i < 8; i++) {
      findItem.bitDate[i] === "3"
        ? newTimeTable.push("2")
        : newTimeTable.push(findItem.bitDate[i]);
    }
    newTimeTable = newTimeTable.join("");
    let newSchedule = [...teacherSchedule];
    let findorigin = newSchedule.find((t) => t.localDate === targetDate[0]);
    findorigin.bitDate = newTimeTable;
    setTeacherSchedule(newSchedule);
    onPut();
  };

  return (
    <>
      {!isLoading && (
        <div className={style.container}>
          <div className={style.inner_container}>
            <div className={style.title}>
              <span className={style.title_name}>{name} </span>
              <span className={style.title_position}>선생님</span>
              <span className={style.title_schedule}>수업 스케쥴</span>
            </div>
            <div className={style.content}>
              <div className={style.date_header}>
                <button
                  className={cnt === 0 ? style.button_disable : style.button}
                  onClick={prevWeek}
                  disabled={cnt === 0 ? true : false}
                >
                  〈&nbsp; 이전
                </button>
                <span className={style.date_text}>
                  {thisYear(0)}.{thisMonth(0)}.{thisDay(0)} ~ {thisYear(6)}.
                  {thisMonth(6)}.{thisDay(6)}
                </span>
                <button
                  className={cnt === 2 ? style.button_disable : style.button}
                  onClick={nextWeek}
                  disabled={cnt === 2 ? true : false}
                >
                  다음 &nbsp;〉
                </button>
              </div>
              <div className={style.date_item}>
                <div className={style.day_start}>
                  <div className={style.day_header}>
                    {thisDay(0)}일 &#40;월&#41;
                  </div>
                  <div className={style.day_item}>
                    {checkSchedule(totalThisDate(0))}
                  </div>
                </div>
                <div className={style.day}>
                  <div className={style.day_header}>
                    {thisDay(1)}일 &#40;화&#41;
                  </div>
                  <div className={style.day_item}>
                    {checkSchedule(totalThisDate(1))}
                  </div>
                </div>
                <div className={style.day}>
                  <div className={style.day_header}>
                    {thisDay(2)}일 &#40;수&#41;
                  </div>
                  <div className={style.day_item}>
                    {checkSchedule(totalThisDate(2))}
                  </div>
                </div>
                <div className={style.day}>
                  <div className={style.day_header}>
                    {thisDay(3)}일 &#40;목&#41;
                  </div>
                  <div className={style.day_item}>
                    {checkSchedule(totalThisDate(3))}
                  </div>
                </div>
                <div className={style.day}>
                  <div className={style.day_header}>
                    {thisDay(4)}일 &#40;금&#41;
                  </div>
                  <div className={style.day_item}>
                    {checkSchedule(totalThisDate(4))}
                  </div>
                </div>
                <div className={style.day}>
                  <div className={style.day_header} style={{ color: "blue" }}>
                    {thisDay(5)}일 &#40;토&#41;
                  </div>
                  <div className={style.day_item}>
                    {checkSchedule(totalThisDate(5))}
                  </div>
                </div>
                <div className={style.day_end}>
                  <div className={style.day_header} style={{ color: "red" }}>
                    {thisDay(6)}일 &#40;일&#41;
                  </div>
                  <div className={style.day_item}>
                    {checkSchedule(totalThisDate(6))}
                  </div>
                </div>
              </div>
              <div className={style.save_button_div}>
                <button className={style.save_button} onClick={onSubmit}>
                  예약하기
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
