import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vite'
import {resolve} from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src') // 配置@指向src目录
    }
  },
  server: {
    host: '0.0.0.0', // 监听所有网卡，允许局域网访问
    port: 5173
  }
})
