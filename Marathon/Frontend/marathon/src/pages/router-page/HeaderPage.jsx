import React from "react";
import Header from "components/common/Header.jsx";
import { Outlet } from "react-router-dom";

export default function HeaderPage() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}
