<template>
  <div id="header">
    <div class="nav d-flex justify-content-between">
      <div class="d-flex" style="font-family: 'Hahmlet', serif;">
        <img :src="logo" alt="BodyFit" id="logo" @click="goHome" />
        <ul>
          <li id="video-category">
            <router-link to="/videos">VIDEOS</router-link>
            <dl>
              <dt><router-link to="/videos">영상 목록</router-link></dt>
              <dd>
                <router-link to="/category/allbody">전신 운동</router-link>
              </dd>
              <dd>
                <router-link to="/category/upper">상체 운동</router-link>
              </dd>
              <dd>
                <router-link to="/category/lower">하체 운동</router-link>
              </dd>
              <dd>
                <router-link to="/category/core">코어 운동</router-link>
              </dd>
              <dd>
                <router-link to="/category/etc">기타 영상</router-link>
              </dd>
            </dl>
          </li>
          <li id="health">
            <router-link to="/gym">주변 헬스장</router-link>
            <dl>
              <dt>
                <router-link to="/gym">주변 헬스장</router-link>
              </dt>
            </dl>
          </li>
          <li id="article">
            <router-link to="/articles">자유게시판</router-link>
            <dl>
              <dt><router-link to="/articles">자유게시판</router-link></dt>
            </dl>
          </li>
          <li id="official">
            <router-link to="/articles/notice/main">공지사항</router-link>
            <dl>
              <dt>
                <router-link to="/articles/notice/main">공지사항</router-link>
              </dt>
            </dl>
          </li>
          <router-link v-if="isAdmin" to="/admin">Admin</router-link>
        </ul>
      </div>
      <div>
        <div
          v-if="users.LoginNickname"
          class="header-button d-flex"
         >
          <router-link
            to="#"
            type="button"
            id="head-button"
            style="width: auto; cursor: auto; font-family: 'Hahmlet', serif;"
            >{{ users.LoginNickname }}님 환영합니다!</router-link
          >
          <li id="myPageContainer" style="font-family: 'Hahmlet', serif;">
            <a href="#" id="myPage">My Page</a>
            <dl>
              <dd><router-link to="/users/myfollow">My 팔로우</router-link></dd>
              <dd>
                <router-link to="/users/myvideo">My 찜한영상</router-link>
              </dd>
              <dd>
                <router-link to="/users/myreview">My 리뷰관리</router-link>
              </dd>
              <dd>
                <router-link to="/users/modify">회원정보 수정</router-link>
              </dd>
              <dd><a href="#" @click="logoutrequest">로그아웃</a></dd>
            </dl>
          </li>
        </div>

        <div v-else class="header-button d-flex">
          <router-link
            to="/users/login"
            type="button"
            id="head-button"
            >로그인</router-link
          >
          <router-link
            to="/users/signup"
            type="button"
            id="head-button"
            style="margin-right: 50px"
            >회원가입</router-link
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  data() {
    return {
      logo: require("@/static/img/logo.png"),
    };
  },
  computed: {
    ...mapState(["users"]),
    ...mapGetters(["loginUser"]),
    isAdmin() {
      if (this.loginUser.rank) {
        return true;
      }
      return false;
    },
  },
  methods: {
    logoutrequest() {
      this.$store.commit("LOGOUTREQUEST");
      this.$router.go();
    },
    goHome() {
      this.$router.push("/");
    },
  },
  created() {
    this.$store.commit("SESSION_CHECK");
  },
};
</script>

<style scoped>
#header {
  width: 100%;
  background: rgb(0, 0, 0);
  margin: 0 auto;
}
#head-button {
  margin-top: 15px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  width: 95px;
  margin-right: 8px;
  font-size: 1.05rem;
}
#head-button:hover {
  background: none;
  color: rgb(145, 145, 145);
}

#head-title {
  font-size: 25px;
  width: auto;
  color: rgb(247, 247, 250);
  margin: 20px 5px 10px 10px;
}
#head-title:hover {
  background: none;
  color: rgb(160, 164, 167);
}
#logo {
  width: 145px;
  height: 60px;
  margin-top: 8px;
  object-fit: cover;
  cursor: pointer;
}

.nav {
  margin: 0 auto;
  width: 1200px;
  height: 80px;
}

.nav ul {
  width: auto;
  height: 50px;
  display: flex;
  position: relative;
  margin: 15px 25px 25px 1px;
  background: rgb(0, 0, 0);
  text-align: center;
  line-height: 50px;
}

.nav li {
  list-style-type: none;
  position: relative;
}

#myPageContainer {
  width: auto;
  height: 50px;
  display: flex;
  position: relative;
  margin: 15px 25px 25px 1px;
  background: rgb(0, 0, 0);
  text-align: center;
  line-height: 50px;
}
#myPageContainer:hover dl {
  height: 290px;
}
#health:hover dl {
  height: 55px;
}
#article:hover dl {
  height: 55px;
}
#official:hover dl {
  height: 55px;
}
#myPage {
  position: relative;
  top: 0px;
  width: 130px;
  height: 55px;
}
#myPageContainer dl {
  width: 130px;
  text-align: center;
}
#myPageContainer dl a {
  width: 130px;
  text-align: center;
}

.nav a {
  text-decoration: none;
  color: rgb(250, 250, 250);
  width: 120px;
  height: 50px;
  display: block;
}

.nav a:hover {
  background: rgb(54, 54, 54);
  color: rgb(160, 164, 167);
}

.nav dl {
  width: 120px;
  height: 0px;
  position: absolute;
  left: 0;
  top: 50px;
  background: rgb(34, 32, 32);
  transition: all 0.3s;
  overflow: hidden;
  z-index: 2;
}
.nav li:hover dl {
  height: 230px;
}
#video-category:hover dl {
  height: 340px;
}
</style>
