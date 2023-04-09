import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import style from "./NoticeManage.module.css";
import { useRecoilState } from "recoil";
import { nowSideNavState } from "@/atoms/common.atom";
import { useLocation, useNavigate } from "react-router-dom";
import { getNoticeList } from "@/connect/axios/queryHooks/notice";

export const NoticeManage = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const page = searchParams.get("page");
  const search = searchParams.get("search") || "";

  const [input, setInput] = useState("");
  const navigate = useNavigate();
  const [nowSideNav, setNowSideNav] = useRecoilState(nowSideNavState);

  const { data: noticeList } = getNoticeList(Number(page), search);

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    if (nowSideNav !== "공지사항") setNowSideNav("공지사항");
  }, []);
  return (
    <div style={{ padding: "40px 3%" }}>
      <Board
        data={noticeList?.content}
        grid={"20% 50% 30%"}
        headRow={["번호", "제목", "날짜"]}
        url={"/mypage/notice/n"}
        type={"noticeAll"}
      />
      <div style={{ animation: "0.7s ease-in-out loadEffect6" }}>
        <div style={{ textAlign: "right" }}>
          <Button
            content="공지작성"
            onClick={() => {
              navigate("/mypage/notice-manage/new");
            }}
            style={{ marginTop: "10px" }}
          />
        </div>
        <Pagenation
          number={noticeList?.number}
          first={noticeList?.first}
          last={noticeList?.last}
          totalPages={noticeList?.totalPages}
          url={`?search=${search ? search : ""}&page=`}
        />
        <div className={style.bottom_search}>
          <Input
            input={input}
            setInput={setInput}
            placeholder={""}
            style={{ width: "45%" }}
          />
          {!input && (
            <div className={style.placeholder}>
              <FontAwesomeIcon icon={faMagnifyingGlass} />
              <span> 제목 + 내용</span>
            </div>
          )}
          <Button
            content="검색"
            onClick={() => {
              navigate(`?search=${input ? input : ""}&page=1`);
            }}
            style={{ margin: "0 5px" }}
          />
        </div>
      </div>
    </div>
  );
};
