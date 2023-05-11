import { useParams } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";
import { addCommas } from "@/pages/code/hooks/func";
import { SearchGroup } from "./searchGroup/SearchGroup";

import style from "./RankingDetailBoard.module.css";

interface BoardColumn {
  author: string;
  submissionTime: string;
  language: string;
  memory: number;
  runtime: number;
  refactoring: number;
  codeLength: number;
}

interface props {
  data: BoardColumn[] | undefined;
}

export const RankingDetailBoard = ({ data }: props) => {
  return (
    <>
      <div className={style.header_container}>
        <p>총 {addCommas(1500)}회</p>
        <SearchGroup />
      </div>
      <div className={style.content_container}>
        {data?.map((v) => {
          return (
            <div key={uuidv4()} className={style.info}>
              <div className={style.master_info}>
                <div className={style.user_info}>
                  <div
                    style={{
                      backgroundColor: "white",
                      width: "2rem",
                      height: "2rem",
                      borderRadius: "100px",
                    }}
                  />
                  <div>{v.author}</div>
                </div>
                <div>제출일 : {v.submissionTime}</div>
              </div>
              <div className={style.info_list}>
                <div>
                  <p>{v.language}</p>
                  <p>언어</p>
                </div>
                <div className={style.vertical_line} />
                <div>
                  <p>{addCommas(v.runtime)}ms</p>
                  <p>실행시간</p>
                </div>
                <div className={style.vertical_line} />
                <div>
                  <p>{addCommas(v.memory)}KB</p>
                  <p>메모리</p>
                </div>
                <div className={style.vertical_line} />
                <div>
                  <p>{v.refactoring}점</p>
                  <p>리팩토링</p>
                </div>
                <div className={style.vertical_line} />
                <div>
                  <p>{addCommas(v.codeLength)}</p>
                  <p>코드길이</p>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </>
  );
};
