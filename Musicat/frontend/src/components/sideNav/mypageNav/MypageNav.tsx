import { nowSideNavState } from "@/atoms/common.atom";
import { logoutUser, userInfoState } from "@/atoms/user.atom";
import { useNavigate } from "react-router-dom";
import { useRecoilValue, useResetRecoilState } from "recoil";
import { v4 as uuidv4 } from "uuid";
import style from "./MypageNav.module.css";

interface MypageNavProps {
  sideNavTitle: string;
  sideNavContent: string[];
  urls: string[];
}

export const MypageNav = ({
  sideNavTitle,
  sideNavContent,
  urls,
}: MypageNavProps) => {
  const nowNav = useRecoilValue(nowSideNavState);
  const clear = useResetRecoilState(userInfoState);
  const navigate = useNavigate();

  return (
    <div
      className={style.side_board}
      style={{ animation: "0.7s ease-in-out loadEffect2" }}
    >
      <h4>{sideNavTitle}</h4>
      <hr />
      {sideNavContent.map((data, i) => {
        return (
          <div
            key={uuidv4()}
            className={
              nowNav === data ? style.now + " " + style.nav : style.nav
            }
            onClick={() => {
              if (data === "로그아웃") {
                logoutUser(clear, navigate);
              } else {
                navigate(urls[i]);
              }
            }}
          >
            {data}
            {nowNav === data ? <div className={style.tag} /> : null}
          </div>
        );
      })}
      <hr />
    </div>
  );
};
