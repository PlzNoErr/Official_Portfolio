import { HTMLAttributes } from "react";
import style from "./Button.module.css";

interface ButtonProps extends HTMLAttributes<HTMLDivElement> {
  content: string;
  onClick: React.MouseEventHandler<HTMLDivElement>;
}

export const Button = ({ content, onClick, ...props }: ButtonProps) => {
  return (
    <>
      <div className={style.button} {...props} onClick={onClick}>
        {content}
      </div>
    </>
  );
};
