<template>
  <header class="navbar">
    <div class="navbar__brand">
      <div class="navbar__logo">
        <i class="el-icon-s-platform"></i>
      </div>
      <div class="navbar__brand-copy">
        <span class="navbar__eyebrow">Campus Navigation Admin</span>
        <strong class="navbar__title">{{ $project.projectName }}</strong>
      </div>
    </div>
    <div class="navbar__right">
      <button class="navbar__portal" type="button" @click="onIndexTap">
        <i class="el-icon-position"></i>
        <span>前台入口</span>
      </button>
      <div class="navbar__user">
        <div class="navbar__avatar">{{ avatarText }}</div>
        <div class="navbar__meta">
          <strong>{{ adminName }}</strong>
          <span>{{ roleName }}</span>
        </div>
      </div>
      <el-button type="primary" class="navbar__logout" @click="onLogout">退出登录</el-button>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      user: {}
    };
  },
  computed: {
    adminName() {
      return this.$storage.get('adminName') || '管理员';
    },
    roleName() {
      return this.$storage.get('role') || '后台角色';
    },
    avatarText() {
      const name = this.adminName;
      return name ? name.substring(0, 1).toUpperCase() : 'A';
    }
  },
  mounted() {
    const sessionTable = this.$storage.get('sessionTable');
    if (!sessionTable) {
      return;
    }
    this.$http({
      url: `${sessionTable}/session`,
      method: 'get'
    }).then(({ data }) => {
      if (data && data.code === 0) {
        this.user = data.data;
        this.$storage.set('userid', data.data.id);
      } else {
        this.$message.error(data.msg);
      }
    });
  },
  methods: {
    onLogout() {
      this.$storage.clear();
      this.$router.replace({
        name: 'login'
      });
    },
    onIndexTap() {
      window.location.href = `${this.$base.indexUrl}`;
    }
  }
};
</script>

<style lang="scss" scoped>
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 18px;
  padding: 18px 24px;
  border: 1px solid rgba(33, 79, 70, 0.1);
  border-radius: 28px;
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.94) 0%, rgba(246, 250, 247, 0.9) 100%);
  box-shadow: 0 16px 40px rgba(32, 54, 48, 0.1);
  backdrop-filter: blur(18px);
}

.navbar__brand {
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
}

.navbar__logo {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 52px;
  height: 52px;
  border-radius: 18px;
  color: #ffffff;
  font-size: 22px;
  background: linear-gradient(135deg, #2f7a69 0%, #214f46 100%);
  box-shadow: 0 14px 28px rgba(47, 122, 105, 0.22);
}

.navbar__brand-copy {
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.navbar__eyebrow {
  margin-bottom: 4px;
  color: #6a7f78;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.navbar__title {
  overflow: hidden;
  color: #203630;
  font-size: 20px;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.navbar__right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.navbar__portal {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  height: 42px;
  padding: 0 16px;
  border: 1px solid rgba(47, 122, 105, 0.16);
  border-radius: 14px;
  background: rgba(47, 122, 105, 0.08);
  color: #2f7a69;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: transform 0.2s ease, background 0.2s ease;
}

.navbar__portal:hover {
  transform: translateY(-1px);
  background: rgba(47, 122, 105, 0.14);
}

.navbar__user {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 8px 6px 6px;
  border-radius: 18px;
  background: rgba(247, 250, 248, 0.92);
  border: 1px solid rgba(33, 79, 70, 0.08);
}

.navbar__avatar {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 38px;
  height: 38px;
  border-radius: 14px;
  color: #ffffff;
  font-weight: 700;
  background: linear-gradient(135deg, #d4aa63 0%, #b8873f 100%);
}

.navbar__meta {
  display: flex;
  flex-direction: column;
  line-height: 1.2;
}

.navbar__meta strong {
  color: #203630;
  font-size: 14px;
}

.navbar__meta span {
  margin-top: 2px;
  color: #6a7f78;
  font-size: 12px;
}

.navbar__logout {
  min-width: 108px;
}

@media (max-width: 1080px) {
  .navbar {
    flex-direction: column;
    align-items: stretch;
  }

  .navbar__right {
    flex-wrap: wrap;
    justify-content: space-between;
  }
}

@media (max-width: 768px) {
  .navbar {
    padding: 18px;
    border-radius: 24px;
  }

  .navbar__title {
    font-size: 18px;
    white-space: normal;
  }

  .navbar__right {
    flex-direction: column;
    align-items: stretch;
  }

  .navbar__portal,
  .navbar__logout {
    width: 100%;
  }
}
</style>
