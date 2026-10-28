<script setup>
import http from "../utils/request.js";
import {onMounted, reactive, ref} from "vue";

const baseURL = import.meta.env.VITE_API_BASE_URL

const path = ref("");
const dirList = ref([])
const fileList = ref([])
const videoRef = ref(null)
const videoSrc = ref("http://127.0.0.1:8080/api/video/stream?path=D:/1-test-dir/a/1.mp4")
const type = ['mp4','webm','mov','ogg','ogv','3gp']
const getPath = async () => {
  let {data} = await http.get("/video/default");
  path.value = data.replace(/\\/g, '/');
}
const getDirs = async () => {
  let {data} = await http.get(`/video/dirs?path=${path.value}`);
  dirList.value = data;
}
const getFiles = async () => {
  let {data} = await http.get(`/video/files?path=${path.value}`);
  fileList.value = data;
}
const openDir = async (dirPath) =>{
  path.value = dirPath;
  await getFiles();
  await getDirs();
}
const openFile = async (fileName) =>{
  const suffix = fileName.split('.').pop();
  if(type.includes((suffix))){
    const video = videoRef.value;
    video.src = baseURL + "/video/stream?path=" +path.value+"/" + fileName
    video.play().catch(err=>console.log('播放失败',err))
  }
}

onMounted(async () => {
  await getPath();
  await getDirs();
  await getFiles();
})
</script>

<template>
  <div class="index">
    <div class="nav">
      <div class="block">
        <div class="block-top">
          <div class="dir-title">{{ path }}</div>
        </div>
        <div class="block-middle">
          <div class="dir-content">
            <div class="dir-lists">
              <div @click="openDir(item)" class="dir-list" v-for="(item,index) in dirList" :key="index">
                <div class="ico"><img src="@/images/dir.png" alt=""></div>
                <div class="name">{{ item.split('/').pop() }}</div>
              </div>
            </div>
            <div class="file-lists">
              <div @click="openFile(item)" class="file-list" v-for="(item,index) in fileList" :key="index">
                <div class="ico"><img src="@/images/file.png" alt=""></div>
                <div class="name">{{ item }}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="block-footer"></div>
      </div>
    </div>
    <div class="content">
      <div class="video">
        <video
            ref="videoRef"
            controls
            width="100%"
            preload="auto"
        ></video>
      </div>
    </div>
    <div class="footer"></div>
  </div>
</template>

<style lang="scss" scoped>

.index {
  padding-top: 1px;
  display: flex;
  align-items: flex-start;


  .nav {
    //width: 250px;
    margin-top: 20px;
    //min-width: 250px;
    max-width: 250px;
    height: 92vh;
    overflow-y: scroll; /* 垂直滚动；overflow:scroll 横竖都滚动 */
    /* Firefox */
    scrollbar-width: none;
    /* 旧版Edge、IE */
    -ms-overflow-style: none;

    .block {
      padding: 0 20px;

      .block-top {
        .dir-title {
          color: #3574F0;
        }
      }

      .block-middle {
        margin-left: 10px;

        .dir-content {
          cursor: pointer;
          .dir-lists {
            margin-top: 10px;

            .dir-list {
              display: flex;
              align-items: center;
              gap: 10px;
              margin-top: 10px;


              .ico {
                img {
                  width: 20px;
                }
              }

              .name {
                white-space: nowrap;      /* 禁止换行，保持一行 */
                overflow: hidden;          /* 隐藏超出部分 */
                text-overflow: ellipsis;   /* 超出显示省略号 ... */
              }
            }
          }

          .file-lists {
            margin-top: 10px;

            .file-list {
              display: flex;
              align-items: center;
              margin-top: 10px;

              .ico {
                img {
                  width: 20px;
                }
              }

              .name {
                white-space: nowrap;      /* 禁止换行，保持一行 */
                overflow: hidden;          /* 隐藏超出部分 */
                text-overflow: ellipsis;   /* 超出显示省略号 ... */
              }
            }
          }
        }
      }
    }
  }

  .content {
    flex: 1;
    background-color: #f6f6f6;
    min-height: 100vh;
    padding: 30px;

    .video {
      overflow: hidden;
      video {
        height: 90vh;
        background-color: #fff;
        border-radius: 10px;
      }
    }
  }
}
</style>