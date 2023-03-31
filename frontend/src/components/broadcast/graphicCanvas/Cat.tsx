/* eslint-disable */
import { useAnimations, useGLTF, useTexture } from "@react-three/drei";
import { GroupProps } from "@react-three/fiber";
import { useEffect, useMemo, useRef, useState } from "react";
import { GLTF } from "three/examples/jsm/loaders/GLTFLoader";
import { ANI_NAME } from "./ani_name";

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

interface propsType extends GroupProps {}

export const Cat = (props: propsType) => {
  const [index, setIndex] = useState(0);

  const { scene } = useGLTF("/broadcast/cat/Chibi_Cat_01.glb") as ExtendedGLTF;

  const animations = ANI_NAME.reduce((acc: any, name) => {
    const { animations } = useGLTF(
      `broadcast/animation/Anim_Chibi@${name}.glb`
    ) as ExtendedGLTF;
    acc.push(animations[0]);
    return acc;
  }, []);

  const { actions, names, ref } = useAnimations(animations);

  useEffect(() => {
    actions[names[index]]?.reset().fadeIn(0.5).play();
    return () => {
      actions[names[index]]?.fadeOut(0.5);
    };
  }, [index, actions, names]);

  const characterClick = () => {
    setIndex((prev) => (prev + 1) % names.length);
  };

  return (
    <group {...props} onClick={characterClick} ref={ref as any}>
      <primitive object={scene} />
    </group>
  );
};
