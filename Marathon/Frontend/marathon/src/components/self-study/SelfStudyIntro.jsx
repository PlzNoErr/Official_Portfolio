import style from "./SelfStudyIntro.module.css";

export default function SelfStudyIntro({ title, gif }) {
  // {type}번째 게임 - {mode}모드 - 도입 화면
  return (
    <div className={style.container}>
      <div className={style.title}>{title}</div>
      <img className={style.gif} src={gif} alt="인트로 gif" />
    </div>
  );
}
