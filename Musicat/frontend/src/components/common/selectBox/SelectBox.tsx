import { v4 as uuidv4 } from "uuid";
import style from "./SelectBox.module.css";
import { HTMLAttributes, useState } from "react";

interface SelectBoxProps extends HTMLAttributes<HTMLSelectElement> {
  options: Options[];
  readonly?: boolean;
  setValue: (val: string) => void;
}

interface Options {
  value: string;
  name: string;
}

/** select-box를 사용할때는 꼭 기본값을 주입해 줘야 한다.
 *  또한 option에 사용될 {value , name} 오브젝트 배열을 주입해 주어야 한다.
 *  setValue의 값도 주입해 주어야 한다. 부모 컴포넌트에서 useState를 통해서
 *  select-box에서 선택된 값을 유지한다.
 */
export const SelectBox = ({
  options,
  defaultValue,
  readonly,
  setValue,
  ...props
}: SelectBoxProps) => {
  const [select, setSelect] = useState(defaultValue);

  return (
    <select
      className={style.select_box}
      value={select}
      onChange={(e) => {
        setValue(e.target.value);
        setSelect(e.target.value);
      }}
      {...props}
    >
      {options.map((option) => (
        <option
          className={style.option}
          key={uuidv4()}
          value={option.value}
          disabled={readonly}
        >
          {option.name}
        </option>
      ))}
    </select>
  );
};
