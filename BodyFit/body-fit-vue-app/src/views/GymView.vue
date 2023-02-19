<template>
  <div class="main-container">
    <h2 style="font-family: 'Gowun Dodum', sans-serif; text-align: center; margin-top: 5px;">주변 헬스장 정보</h2>
    <div class="mx-auto" style="border: 1px gray solid; margin-top: 20px;">
      <div id="map"></div>
    </div>
    <div class="my-2">
      <table class="table" style="font-family: 'Gowun Batang', serif">
        <thead>
          <tr>
            <td>헬스장</td>
            <td>주소</td>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(gym, index) in gyms.gymList" :key="index">
            <td>{{ gym.gymName }}</td>
            <td>{{ gym.address }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
export default {
  name: "GymView",
  data() {
    return {
      map: null,
      markers: [],
      infowindow: null,
      latitude: 37.50128965157738,
      longitude: 127.03974037561257,
    };
  },
  computed: {
    ...mapState(["gyms"]),
    ...mapGetters(["loginUser"]),
    gymlist() {
      return this.gyms.gymList;
    },
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.50128965157738, 127.03974037561257),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
      navigator.geolocation.getCurrentPosition((pos) => {
        let latitude = pos.coords.latitude;
        let longitude = pos.coords.longitude;
        this.$store.dispatch("searchGym", {
          latitude: latitude,
          longitude: longitude,
        });
      });
    },
    moveMap(latitude, longitude) {
      let moveLatLon = new kakao.maps.LatLng(latitude, longitude);

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
    },
    changeSize(size) {
      const container = document.getElementById("map");
      container.style.width = `${size}px`;
      container.style.height = `${size}px`;
      this.map.relayout();
    },
    displayMarker() {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      let positions = this.gyms.gymList.map(
        (position) =>
          new kakao.maps.LatLng(position.latitude, position.longitude)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    displayInfoWindow() {
      if (this.infowindow && this.infowindow.getMap()) {
        //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
        this.map.setCenter(this.infowindow.getPosition());
        return;
      }

      var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(
          37.50128965157738,
          127.03974037561257
        ), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.map.setCenter(iwPosition);
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
      document.head.appendChild(script);
    }
  },
  watch: {
    gymlist() {
      this.moveMap(this.latitude, this.longitude);
      this.displayMarker();
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 500px;
}
thead {
  background: rgba(0, 0, 0, 0.1);
  font-size: 1.2rem;
}
</style>
