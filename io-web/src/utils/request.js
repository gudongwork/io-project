import axios from 'axios'

// 创建实例
const service = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    timeout: 10000
})

// 请求拦截器
service.interceptors.request.use(config => {
    // token 注入
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
})

// 响应拦截器
service.interceptors.response.use(
    resp => resp.data,
    err => Promise.reject(err)
)

/**
 * Rust风格请求函数，返回 Result { ok:boolean, data?, err? }
 * @param {Object} config axios config
 * @returns Promise<{ok: boolean, data?: any, err?: {code: number, msg: string}}>
 */
async function request(config) {
    try {
        const data = await service(config)
        return data
        // if (data.code === 0) {
        // } else {
        //     // 业务层面错误
        //     return {
        //         ok: false,
        //         err: { code: data.code, msg: data.message || '业务请求失败' }
        //     }
        // }
    } catch (rawErr) {
        // 网络/axios异常
        const code = rawErr.response?.status || -1
        return {
            ok: false,
            err: {
                code,
                msg: rawErr.message || '网络异常'
            }
        }
    }
}

// 快捷方法
const http = {
    get: (url, params) => request({ method: 'GET', url, params }),
    post: (url, data) => request({ method: 'POST', url, data }),
    put: (url, data) => request({ method: 'PUT', url, data }),
    delete: (url, params) => request({ method: 'DELETE', url, params })
}

export default http