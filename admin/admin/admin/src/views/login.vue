<template>
  <div class="login-view">
    <div class="login-view__overlay"></div>
    <div class="login-view__panel">
      <section class="login-view__hero">
        <div class="login-view__hero-badge">Campus Navigation Management</div>
        <img :src="logo" alt="logo" class="login-view__logo" />
        <h1>{{ $project.projectName }}</h1>
        <p>统一维护校园简介、建筑速看、公告内容与服务信息，让管理端更清晰也更高效。</p>
        <div class="login-view__hero-points">
          <span>内容统一发布</span>
          <span>校园信息可视化维护</span>
          <span>后台操作更聚焦</span>
        </div>
      </section>

      <el-form class="login-card" @keyup.enter.native="login">
        <div class="login-card__eyebrow">管理后台登录</div>
        <div class="login-card__title">欢迎回来</div>
        <div class="login-card__subtitle">输入账号密码，进入校园导航后台。</div>

        <div v-if="loginType === 1" class="login-card__field">
          <label>用户名</label>
          <input
            v-model="rulesForm.username"
            name="username"
            type="text"
            placeholder="请输入用户名"
          />
        </div>

        <div v-if="loginType === 1" class="login-card__field">
          <label>密码</label>
          <input
            v-model="rulesForm.password"
            name="password"
            type="password"
            placeholder="请输入密码"
          />
        </div>

        <div v-if="roles.length > 1" class="login-card__roles">
          <div class="login-card__roles-title">登录角色</div>
          <el-radio-group v-model="rulesForm.role">
            <el-radio v-for="item in roles" :key="item.roleName" :label="item.roleName">
              {{ item.roleName }}
            </el-radio>
          </el-radio-group>
        </div>

        <div v-if="roles.length === 1" class="login-card__hint">
          当前默认角色：{{ roles[0].roleName }}
        </div>

        <el-button
          v-if="loginType === 1"
          type="primary"
          class="login-card__submit"
          @click="login"
        >
          登录系统
        </el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
import menu from '@/utils/menu'
import logo from '@/assets/img/logo.png'

export default {
  data() {
    return {
      baseUrl: this.$base.url,
      loginType: 1,
      logo,
      rulesForm: {
        username: '',
        password: '',
        role: '',
        code: ''
      },
      menus: [],
      roles: [],
      tableName: '',
      codes: [
        {
          num: 1,
          color: '#000',
          rotate: '10deg',
          size: '16px'
        },
        {
          num: 2,
          color: '#000',
          rotate: '10deg',
          size: '16px'
        },
        {
          num: 3,
          color: '#000',
          rotate: '10deg',
          size: '16px'
        },
        {
          num: 4,
          color: '#000',
          rotate: '10deg',
          size: '16px'
        }
      ]
    };
  },
  mounted() {
    const menus = menu.list();
    this.menus = menus;

    for (let index = 0; index < this.menus.length; index += 1) {
      if (this.menus[index].hasBackLogin === '是') {
        this.roles.push(this.menus[index]);
      }
    }
  },
  created() {
    this.getRandCode();
  },
  methods: {
    register(tableName) {
      this.$storage.set('loginTable', tableName);
      this.$storage.set('pageFlag', 'register');
      this.$router.push({ path: '/register' });
    },
    login() {
      if (!this.rulesForm.username) {
        this.$message.error('请输入用户名');
        return;
      }
      if (!this.rulesForm.password) {
        this.$message.error('请输入密码');
        return;
      }
      if (this.roles.length > 1) {
        if (!this.rulesForm.role) {
          this.$message.error('请选择角色');
          return;
        }

        for (let index = 0; index < this.menus.length; index += 1) {
          if (this.menus[index].roleName === this.rulesForm.role) {
            this.tableName = this.menus[index].tableName;
          }
        }
      } else {
        this.tableName = this.roles[0].tableName;
        this.rulesForm.role = this.roles[0].roleName;
      }

      this.$http({
        url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
        method: 'post'
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$storage.set('Token', data.token);
          this.$storage.set('role', this.rulesForm.role);
          this.$storage.set('sessionTable', this.tableName);
          this.$storage.set('adminName', this.rulesForm.username);
          this.$router.replace({ path: '/index/' });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    getRandCode(len = 4) {
      this.randomString(len);
    },
    randomString(len = 4) {
      const chars = [
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
        '3', '4', '5', '6', '7', '8', '9'
      ];
      const colors = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'];
      const sizes = ['14', '15', '16', '17', '18'];

      for (let index = 0; index < len; index += 1) {
        let key = Math.floor(Math.random() * chars.length);
        this.codes[index].num = chars[key];

        let code = '#';
        for (let colorIndex = 0; colorIndex < 6; colorIndex += 1) {
          key = Math.floor(Math.random() * colors.length);
          code += colors[key];
        }
        this.codes[index].color = code;

        let rotate = Math.floor(Math.random() * 60);
        if (Math.floor(Math.random() * 2) === 1) {
          rotate = `-${rotate}`;
        }
        this.codes[index].rotate = `rotate(${rotate}deg)`;

        key = Math.floor(Math.random() * sizes.length);
        this.codes[index].size = `${sizes[key]}px`;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.login-view {
  position: relative;
  min-height: 100vh;
  padding: 32px;
  background:
    radial-gradient(circle at 12% 12%, rgba(212, 170, 99, 0.18), transparent 18%),
    radial-gradient(circle at 85% 18%, rgba(255, 255, 255, 0.08), transparent 14%),
    linear-gradient(135deg, #16362f 0%, #224a41 45%, #102824 100%);
}

.login-view__overlay {
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 18% 18%, rgba(212, 170, 99, 0.22), transparent 18%),
    radial-gradient(circle at 78% 82%, rgba(47, 122, 105, 0.18), transparent 20%);
}

.login-view__panel {
  position: relative;
  z-index: 1;
  display: grid;
  grid-template-columns: minmax(0, 1.1fr) minmax(360px, 460px);
  gap: 28px;
  align-items: stretch;
  min-height: calc(100vh - 64px);
}

.login-view__hero,
.login-card {
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 32px;
  backdrop-filter: blur(20px);
}

.login-view__hero {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 52px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.14) 0%, rgba(255, 255, 255, 0.08) 100%);
  color: #ffffff;
  box-shadow: 0 30px 60px rgba(0, 0, 0, 0.12);
}

.login-view__hero-badge {
  display: inline-flex;
  align-self: flex-start;
  padding: 8px 14px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.14);
  color: rgba(255, 255, 255, 0.9);
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.1em;
  text-transform: uppercase;
}

.login-view__logo {
  width: 84px;
  height: 84px;
  margin: 28px 0 20px;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.12);
  padding: 12px;
}

.login-view__hero h1 {
  max-width: 640px;
  font-size: 42px;
  line-height: 1.2;
}

.login-view__hero p {
  max-width: 560px;
  margin-top: 18px;
  color: rgba(255, 255, 255, 0.76);
  font-size: 16px;
  line-height: 1.9;
}

.login-view__hero-points {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 28px;
}

.login-view__hero-points span {
  padding: 10px 16px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.12);
  color: rgba(255, 255, 255, 0.92);
  font-size: 14px;
}

.login-card {
  align-self: center;
  width: 100%;
  padding: 34px 32px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.96) 0%, rgba(247, 250, 248, 0.92) 100%);
  box-shadow: 0 28px 60px rgba(10, 20, 18, 0.14);
}

.login-card__eyebrow {
  color: #2f7a69;
  font-size: 13px;
  font-weight: 700;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.login-card__title {
  margin-top: 10px;
  color: #203630;
  font-size: 34px;
  font-weight: 700;
}

.login-card__subtitle {
  margin-top: 10px;
  color: #6a7f78;
  line-height: 1.8;
}

.login-card__field {
  margin-top: 20px;
}

.login-card__field label {
  display: block;
  margin-bottom: 10px;
  color: #526660;
  font-size: 13px;
  font-weight: 700;
}

.login-card__field input {
  width: 100%;
  height: 50px;
  padding: 0 16px;
  border: 1px solid rgba(33, 79, 70, 0.14);
  border-radius: 16px;
  outline: none;
  background: rgba(247, 250, 248, 0.96);
  color: #203630;
  font-size: 14px;
  transition: border-color 0.2s ease, box-shadow 0.2s ease, background 0.2s ease;
}

.login-card__field input:focus {
  border-color: rgba(47, 122, 105, 0.64);
  background: #ffffff;
  box-shadow: 0 0 0 4px rgba(47, 122, 105, 0.12);
}

.login-card__roles {
  margin-top: 22px;
  padding: 18px;
  border-radius: 20px;
  background: rgba(47, 122, 105, 0.06);
}

.login-card__roles-title {
  margin-bottom: 12px;
  color: #526660;
  font-size: 13px;
  font-weight: 700;
}

.login-card__roles /deep/ .el-radio {
  margin-right: 18px;
  margin-bottom: 8px;
}

.login-card__roles /deep/ .el-radio__inner {
  border-color: rgba(47, 122, 105, 0.36);
}

.login-card__roles /deep/ .el-radio__input.is-checked .el-radio__inner {
  background: #2f7a69;
  border-color: #2f7a69;
}

.login-card__roles /deep/ .el-radio__input.is-checked + .el-radio__label {
  color: #203630;
  font-weight: 700;
}

.login-card__hint {
  margin-top: 18px;
  color: #6a7f78;
  font-size: 13px;
}

.login-card__submit {
  width: 100%;
  height: 50px !important;
  margin-top: 28px;
  border: none !important;
  border-radius: 16px !important;
  font-size: 16px !important;
  font-weight: 700 !important;
  background: linear-gradient(135deg, #2f7a69 0%, #214f46 100%) !important;
  box-shadow: 0 16px 30px rgba(47, 122, 105, 0.24);
}

@media (max-width: 1080px) {
  .login-view {
    padding: 20px;
  }

  .login-view__panel {
    grid-template-columns: 1fr;
  }

  .login-view__hero {
    min-height: 360px;
    padding: 36px;
  }
}

@media (max-width: 768px) {
  .login-view {
    padding: 14px;
  }

  .login-view__panel {
    min-height: auto;
  }

  .login-view__hero,
  .login-card {
    padding: 24px;
    border-radius: 24px;
  }

  .login-view__hero h1 {
    font-size: 30px;
  }

  .login-card__title {
    font-size: 28px;
  }
}
</style>
