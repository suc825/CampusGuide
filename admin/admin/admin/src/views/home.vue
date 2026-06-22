<template>
  <div class="home-dashboard">
    <section class="home-dashboard__hero">
      <div class="home-dashboard__hero-copy">
        <div class="home-dashboard__eyebrow">Campus Navigation Dashboard</div>
        <h1>{{ $project.projectName }}</h1>
        <p>
          欢迎进入校园导航管理后台。这里可以统一维护校园简介、建筑速看、公告内容以及其他业务模块，
          让后台操作更清楚，信息维护更高效。
        </p>
        <div class="home-dashboard__hero-actions">
          <el-button type="primary" @click="goRoute('center')">查看个人信息</el-button>
          <el-button @click="goRoute('updatePassword')">修改登录密码</el-button>
        </div>
      </div>

      <div class="home-dashboard__hero-stats">
        <div v-for="item in statCards" :key="item.label" class="home-stat-card">
          <span>{{ item.label }}</span>
          <strong>{{ item.value }}</strong>
          <small>{{ item.description }}</small>
        </div>
      </div>
    </section>

    <section class="home-dashboard__section">
      <div class="home-dashboard__section-head">
        <div>
          <h2>快捷入口</h2>
          <p>常用模块一键直达，减少来回查找菜单的时间。</p>
        </div>
      </div>

      <div class="home-dashboard__action-grid">
        <button
          v-for="(item, index) in quickActions"
          :key="`${item.tableName}-${index}`"
          class="home-action-card"
          type="button"
          @click="goRoute(item.tableName)"
        >
          <div class="home-action-card__icon">
            <i :class="iconByIndex(index)"></i>
          </div>
          <div class="home-action-card__body">
            <strong>{{ item.menu }}</strong>
            <span>{{ item.groupName }}</span>
          </div>
          <i class="el-icon-arrow-right home-action-card__arrow"></i>
        </button>
      </div>
    </section>

    <section class="home-dashboard__bottom">
      <div class="home-dashboard__panel">
        <h2>当前菜单分组</h2>
        <div class="home-dashboard__groups">
          <div v-for="group in menuGroups" :key="group.menu" class="home-group-card">
            <strong>{{ group.menu }}</strong>
            <span>{{ (group.child || []).length }} 个功能入口</span>
          </div>
        </div>
      </div>

      <div class="home-dashboard__panel">
        <h2>使用建议</h2>
        <ul class="home-dashboard__tips">
          <li>先维护校园简介与建筑速看，用户进入小程序后获取的信息会更完整。</li>
          <li>校园公告建议定期更新，保证通知内容及时同步到前台。</li>
          <li>如需提升账号安全性，可以先进入个人中心修改密码。</li>
        </ul>
      </div>
    </section>
  </div>
</template>

<script>
import router from '@/router/router-static'
import menu from '@/utils/menu'

export default {
  data() {
    return {
      iconList: [
        'el-icon-s-home',
        'el-icon-s-management',
        'el-icon-document',
        'el-icon-location',
        'el-icon-message',
        'el-icon-notebook-1',
        'el-icon-s-data',
        'el-icon-odometer'
      ]
    };
  },
  computed: {
    roleMenu() {
      const role = this.$storage.get('role');
      const menus = menu.list() || [];
      for (let index = 0; index < menus.length; index += 1) {
        if (menus[index].roleName === role) {
          return menus[index];
        }
      }
      return {
        roleName: role,
        backMenu: []
      };
    },
    menuGroups() {
      return this.roleMenu.backMenu || [];
    },
    quickActions() {
      const actions = [];
      this.menuGroups.forEach(group => {
        (group.child || []).forEach(child => {
          actions.push({
            groupName: group.menu,
            menu: child.menu,
            tableName: child.tableName
          });
        });
      });
      return actions.slice(0, 6);
    },
    statCards() {
      return [
        {
          label: '当前角色',
          value: this.roleMenu.roleName || '未登录',
          description: '当前账号身份'
        },
        {
          label: '功能入口',
          value: this.quickActions.length,
          description: '常用业务模块'
        },
        {
          label: '菜单分组',
          value: this.menuGroups.length,
          description: '后台导航结构'
        },
        {
          label: '当前日期',
          value: this.todayText(),
          description: '系统时间'
        }
      ];
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      if (this.$storage.get('Token')) {
        this.$http({
          url: `${this.$storage.get('sessionTable')}/session`,
          method: 'get'
        }).then(({ data }) => {
          if (data && data.code !== 0) {
            router.push({ name: 'login' });
          }
        });
      } else {
        router.push({ name: 'login' });
      }
    },
    todayText() {
      const now = new Date();
      const weekMap = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
      return `${now.getFullYear()}-${this.pad(now.getMonth() + 1)}-${this.pad(now.getDate())} ${weekMap[now.getDay()]}`;
    },
    pad(value) {
      return value < 10 ? `0${value}` : `${value}`;
    },
    iconByIndex(index) {
      return this.iconList[index % this.iconList.length];
    },
    goRoute(path) {
      this.$router.push(`/${path}`);
    }
  }
};
</script>

<style lang="scss" scoped>
.home-dashboard {
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.home-dashboard__hero {
  display: grid;
  grid-template-columns: minmax(0, 1.25fr) minmax(320px, 420px);
  gap: 20px;
  padding: 10px;
  border-radius: 30px;
  background:
    radial-gradient(circle at top right, rgba(212, 170, 99, 0.18), transparent 25%),
    linear-gradient(135deg, rgba(37, 76, 67, 0.98) 0%, rgba(27, 58, 51, 0.96) 100%);
  box-shadow: 0 24px 50px rgba(32, 54, 48, 0.18);
}

.home-dashboard__hero-copy,
.home-dashboard__hero-stats {
  border-radius: 24px;
}

.home-dashboard__hero-copy {
  padding: 34px;
  color: #ffffff;
}

.home-dashboard__eyebrow {
  display: inline-block;
  margin-bottom: 14px;
  padding: 8px 14px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.12);
  color: rgba(255, 255, 255, 0.9);
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.home-dashboard__hero-copy h1 {
  font-size: 34px;
  line-height: 1.25;
}

.home-dashboard__hero-copy p {
  max-width: 620px;
  margin-top: 16px;
  color: rgba(255, 255, 255, 0.78);
  line-height: 1.9;
}

.home-dashboard__hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 28px;
}

.home-dashboard__hero-actions .el-button {
  min-width: 148px;
}

.home-dashboard__hero-stats {
  display: grid;
  gap: 14px;
  align-content: center;
  padding: 22px;
}

.home-stat-card {
  padding: 18px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.08);
  color: #ffffff;
}

.home-stat-card span {
  display: block;
  color: rgba(255, 255, 255, 0.72);
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.home-stat-card strong {
  display: block;
  margin: 10px 0 6px;
  font-size: 26px;
}

.home-stat-card small {
  color: rgba(255, 255, 255, 0.7);
}

.home-dashboard__section,
.home-dashboard__panel {
  padding: 26px;
  border: 1px solid rgba(33, 79, 70, 0.1);
  border-radius: 28px;
  background: rgba(255, 255, 255, 0.88);
  box-shadow: 0 16px 40px rgba(32, 54, 48, 0.08);
}

.home-dashboard__section-head h2,
.home-dashboard__panel h2 {
  color: #203630;
  font-size: 22px;
}

.home-dashboard__section-head p {
  margin-top: 8px;
  color: #6a7f78;
}

.home-dashboard__action-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
  margin-top: 22px;
}

.home-action-card {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 18px;
  border: 1px solid rgba(33, 79, 70, 0.1);
  border-radius: 22px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.96) 0%, rgba(244, 248, 245, 0.92) 100%);
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;
}

.home-action-card:hover {
  transform: translateY(-3px);
  border-color: rgba(47, 122, 105, 0.16);
  box-shadow: 0 16px 30px rgba(32, 54, 48, 0.12);
}

.home-action-card__icon {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  width: 52px;
  height: 52px;
  margin-right: 14px;
  border-radius: 18px;
  color: #ffffff;
  font-size: 20px;
  background: linear-gradient(135deg, #2f7a69 0%, #214f46 100%);
  box-shadow: 0 12px 24px rgba(47, 122, 105, 0.2);
}

.home-action-card__body {
  display: flex;
  flex: 1;
  flex-direction: column;
  align-items: flex-start;
}

.home-action-card__body strong {
  color: #203630;
  font-size: 16px;
}

.home-action-card__body span {
  margin-top: 6px;
  color: #6a7f78;
  font-size: 13px;
}

.home-action-card__arrow {
  color: #93a59f;
  font-size: 16px;
}

.home-dashboard__bottom {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 20px;
}

.home-dashboard__groups {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
  margin-top: 20px;
}

.home-group-card {
  padding: 18px;
  border: 1px solid rgba(33, 79, 70, 0.08);
  border-radius: 20px;
  background: rgba(247, 250, 248, 0.95);
}

.home-group-card strong {
  display: block;
  color: #203630;
}

.home-group-card span {
  display: block;
  margin-top: 8px;
  color: #6a7f78;
  font-size: 13px;
}

.home-dashboard__tips {
  margin-top: 18px;
  padding-left: 18px;
  color: #526660;
  line-height: 1.9;
}

@media (max-width: 1200px) {
  .home-dashboard__action-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 960px) {
  .home-dashboard__hero,
  .home-dashboard__bottom {
    grid-template-columns: 1fr;
  }

  .home-dashboard__groups {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .home-dashboard__hero-copy,
  .home-dashboard__hero-stats,
  .home-dashboard__section,
  .home-dashboard__panel {
    padding: 20px;
  }

  .home-dashboard__hero-copy h1 {
    font-size: 28px;
  }

  .home-dashboard__action-grid {
    grid-template-columns: 1fr;
  }

  .home-dashboard__hero-actions .el-button {
    width: 100%;
  }
}
</style>
