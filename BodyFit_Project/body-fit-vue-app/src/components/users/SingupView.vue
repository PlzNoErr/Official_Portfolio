<template>
  <div class="login-wrap" style="margin-top: 10px;">
    <form class="login-html" @submit.prevent="signupcheck">
      <input id="tab-1" type="radio" name="tab" class="sign-in" checked /><label
        for="tab-1"
        class="tab"
        >회원가입</label
      >
      <input id="tab-2" type="radio" name="tab" class="sign-up" /><label
        for="tab-2"
        class="tab"
      ></label>
      <div class="login-form">
        <div class="sign-in-htm">
          <div class="group">
            <label for="user" class="label">id</label>
            <input id="user" type="text" class="input" v-model="signupinfo.id" />
          </div>
          <div class="group">
            <label for="pass" class="label">Password</label>
            <input
              id="pass"
              type="password"
              class="input"
              data-type="password"
              v-model="signupinfo.password"
            />
          </div>
          <div class="group">
            <label for="user" class="label">Nickname</label>
            <input id="user" type="text" class="input" v-model="signupinfo.nickName" />
          </div>
          <div class="group">
            <label for="user" class="label">Email</label>
            <input id="user" type="text" class="input" v-model="signupinfo.email" />
          </div>
          <br />
          <div class="group">
            <input
              type="submit"
              class="button"
              value="SIGN UP"
              style="color: white;"
            />
          </div>
          <div class="hr"></div>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import $ from "@/util/http-common";

export default {
  data() {
    return {
      signupinfo: {
        id: "",
        password: "",
        email: "",
        nickName: "",
      },
    };
  },
  methods: {
    signupcheck() {
      if (
        this.signupinfo.id === "" ||
        this.signupinfo.password === "" ||
        this.signupinfo.email === "" ||
        this.signupinfo.nickName === ""
      ) {
        alert("회원정보를 빠짐 없이 모두 입력해 주세요!");
        return;
      }

      const URL = `/api/users/checksignup`;
      $({
        url: URL,
        method: "POST",
        data: {
          userId: this.signupinfo.id,
          nickName: this.signupinfo.nickName,
        },
      })
        .then((res) => {
          if (res.data.userId) {
            alert("중복된 아이디 입니다! 새로운 아이디를 입력해 주세요!");
            return;
          }
          if (res.data.nickName) {
            alert("중복된 닉네임 입니다! 새로운 닉네임을 입력해 주세요!");
            return;
          }

          this.$store.dispatch("signupRegist", this.signupinfo);
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>

<style scoped>
body {
  margin: 0;
  color: #6a6f8c;
  background: #c8c8c8;
  font: 600 16px/18px "Open Sans", sans-serif;
}
*,
:after,
:before {
  box-sizing: border-box;
}
.clearfix:after,
.clearfix:before {
  content: "";
  display: table;
}
.clearfix:after {
  clear: both;
  display: block;
}
a {
  color: inherit;
  text-decoration: none;
}

.login-wrap {
  width: 100%;
  margin: auto;
  max-width: 525px;
  min-height: 700px;
  position: relative;
  box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24),
    0 17px 50px 0 rgba(0, 0, 0, 0.19);
}
.login-html {
  width: 100%;
  height: 100%;
  position: absolute;
  padding: 60px 70px 50px 70px;
  background: rgba(244, 244, 246, 0.9);
}
.login-html .sign-in-htm,
.login-html .sign-up-htm {
  margin-top: 20px;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  transform: rotateY(180deg);
  backface-visibility: hidden;
  transition: all 0.4s linear;
}
.login-html .sign-in,
.login-html .sign-up,
.login-form .group .check {
  display: none;
}
.login-html .tab,
.login-form .group .label,
.login-form .group .button {
  text-transform: uppercase;
}
.login-html .tab {
  font-size: 22px;
  margin-right: 15px;
  padding-bottom: 5px;
  margin: 0 15px 10px 0;
  display: inline-block;
  border-bottom: 2px solid transparent;
}
.login-html .sign-in:checked + .tab,
.login-html .sign-up:checked + .tab {
  color: rgb(69, 65, 65);
  border-color: #24272c;
}
.login-form {
  min-height: 345px;
  position: relative;
  perspective: 1000px;
  transform-style: preserve-3d;
}
.login-form .group {
  margin-bottom: 15px;
}
.login-form .group .label,
.login-form .group .input,
.login-form .group .button {
  width: 100%;
  color: rgb(72, 69, 69);
  display: block;
}
.login-form .group .input,
.login-form .group .button {
  border: none;
  padding: 15px 20px;
  border-radius: 25px;
  background: rgba(16, 16, 16, 0.1);
}
.login-form .group input[data-type="password"] {
  text-security: circle;
  -webkit-text-security: circle;
}
.login-form .group .label {
  color: rgb(129, 129, 129);
  font-size: 12px;
}
.login-form .group .button {
  background-color: #2a3952;
}
.login-form .group label .icon {
  width: 15px;
  height: 15px;
  border-radius: 2px;
  position: relative;
  display: inline-block;
  background: rgba(255, 255, 255, 0.1);
}
.login-form .group label .icon:before,
.login-form .group label .icon:after {
  content: "";
  width: 10px;
  height: 2px;
  background: #fff;
  position: absolute;
  transition: all 0.2s ease-in-out 0s;
}
.login-form .group label .icon:before {
  left: 3px;
  width: 5px;
  bottom: 6px;
  transform: scale(0) rotate(0);
}
.login-form .group label .icon:after {
  top: 6px;
  right: 0;
  transform: scale(0) rotate(0);
}
.login-form .group .check:checked + label {
  color: #fff;
}
.login-form .group .check:checked + label .icon {
  background: #1161ee;
}
.login-form .group .check:checked + label .icon:before {
  transform: scale(1) rotate(45deg);
}
.login-form .group .check:checked + label .icon:after {
  transform: scale(1) rotate(-45deg);
}
.login-html
  .sign-in:checked
  + .tab
  + .sign-up
  + .tab
  + .login-form
  .sign-in-htm {
  transform: rotate(0);
}
.login-html .sign-up:checked + .tab + .login-form .sign-up-htm {
  transform: rotate(0);
}

.hr {
  height: 2px;
  margin: 60px 0 50px 0;
  background: rgba(43, 42, 42, 0.2);
}
.foot-lnk {
  text-align: center;
}
</style>
