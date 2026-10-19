<script setup>
import http from "../utils/request.js";
import {onMounted, reactive, ref} from "vue";


onMounted(async () => {
  await getEmployees();
})
</script>

<template>
  <div class="index">
    <div class="video">
      <video
          src="http://127.0.0.1:8080/api/video/stream?path=D:/1-test-dir/a/1.mp4"
          controls
          width="100%"
          preload="auto"
      ></video>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.index{
  .img{
    width: 100px;
    height: 100px;
    border-radius: 50%;
    overflow: hidden;
  }
  padding-top: 1px;
  .video{
    width: 80%;
    margin:100px auto;
  }
}
</style>