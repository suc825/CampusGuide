<template>
  <div class="menu-preview">
    <div class="menu-preview__summary">
      <div class="menu-preview__summary-icon">
        <i class="el-icon-s-home"></i>
      </div>
      <div class="menu-preview__summary-copy">
        <strong>后台管理</strong>
        <span>{{ role || '未选择角色' }}</span>
      </div>
    </div>
    <el-scrollbar class="menu-preview__scroll" wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :default-openeds="defaultOpeneds"
        :unique-opened="false"
        class="menu-preview__menu"
      >
        <el-menu-item index="/" @click="menuHandler('')">
          <i class="el-icon-s-home"></i>
          <span slot="title">系统首页</span>
        </el-menu-item>

        <el-submenu index="profile">
          <template slot="title">
            <i class="el-icon-user-solid"></i>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/updatePassword" @click="menuHandler('updatePassword')">修改密码</el-menu-item>
          <el-menu-item index="/center" @click="menuHandler('center')">个人信息</el-menu-item>
        </el-submenu>

        <el-submenu
          v-for="(group, index) in menuGroups"
          :key="group.menu"
          :index="`group-${index}`"
        >
          <template slot="title">
            <i :class="iconByIndex(index)"></i>
            <span>{{ group.menu }}</span>
          </template>
          <el-menu-item
            v-for="(child, childIndex) in group.child"
            :key="`${group.menu}-${childIndex}`"
            :index="`/${child.tableName}`"
            @click="menuHandler(child.tableName)"
          >
            {{ child.menu }}
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import menu from '@/utils/menu'

export default {
  data() {
    return {
      menuList: {},
      role: '',
      icons: [
        'el-icon-s-management',
        'el-icon-menu',
        'el-icon-document',
        'el-icon-s-data',
        'el-icon-location',
        'el-icon-message',
        'el-icon-notebook-1',
        'el-icon-odometer',
        'el-icon-s-custom'
      ]
    };
  },
  computed: {
    activeMenu() {
      return this.$route.path || '/';
    },
    defaultOpeneds() {
      return [];
    },
    menuGroups() {
      return this.menuList.backMenu || [];
    }
  },
  mounted() {
    const menus = menu.list();
    this.role = this.$storage.get('role');

    if (menus && menus.length) {
      for (let index = 0; index < menus.length; index += 1) {
        if (menus[index].roleName === this.role) {
          this.menuList = menus[index];
          break;
        }
      }
    }

    if (!this.menuList.backMenu) {
      this.menuList = { backMenu: [] };
    }
  },
  methods: {
    iconByIndex(index) {
      return this.icons[index % this.icons.length];
    },
    menuHandler(name) {
      this.$router.push(`/${name}`);
    }
  }
};
</script>

<style lang="scss" scoped>
.menu-preview {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 22px 18px;
  border-radius: 32px;
  background:
    radial-gradient(circle at top right, rgba(212, 170, 99, 0.2), transparent 28%),
    linear-gradient(180deg, rgba(31, 60, 52, 0.98) 0%, rgba(22, 45, 39, 0.96) 100%);
  box-shadow: 0 24px 48px rgba(20, 36, 32, 0.22);
}

.menu-preview__summary {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.08);
}

.menu-preview__summary-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 46px;
  height: 46px;
  border-radius: 16px;
  background: linear-gradient(135deg, #d4aa63 0%, #b8873f 100%);
  color: #ffffff;
  font-size: 20px;
}

.menu-preview__summary-copy {
  display: flex;
  flex-direction: column;
}

.menu-preview__summary-copy strong {
  color: #ffffff;
  font-size: 16px;
}

.menu-preview__summary-copy span {
  margin-top: 4px;
  color: rgba(236, 244, 240, 0.7);
  font-size: 12px;
}

.menu-preview__scroll {
  flex: 1;
  margin-top: 18px;
}

.menu-preview__scroll /deep/ .scrollbar-wrapper {
  overflow-x: hidden;
}

.menu-preview__menu {
  border-right: none;
  background: transparent;
}

.menu-preview__menu /deep/ .el-menu-item,
.menu-preview__menu /deep/ .el-submenu__title {
  display: flex;
  align-items: center;
  gap: 12px;
  height: 50px;
  line-height: 50px;
  margin-bottom: 8px;
  padding: 0 16px !important;
  border-radius: 16px;
  color: rgba(236, 244, 240, 0.76);
  background: transparent;
  transition: background 0.2s ease, color 0.2s ease, transform 0.2s ease;
}

.menu-preview__menu /deep/ .el-menu-item i,
.menu-preview__menu /deep/ .el-submenu__title i {
  color: inherit;
}

.menu-preview__menu /deep/ .el-menu-item:hover,
.menu-preview__menu /deep/ .el-submenu__title:hover {
  background: rgba(255, 255, 255, 0.08);
  color: #ffffff;
}

.menu-preview__menu /deep/ .el-menu-item.is-active,
.menu-preview__menu /deep/ .el-submenu.is-active > .el-submenu__title {
  color: #ffffff;
  background: linear-gradient(135deg, rgba(47, 122, 105, 0.9) 0%, rgba(33, 79, 70, 0.92) 100%);
  box-shadow: 0 14px 28px rgba(33, 79, 70, 0.22);
}

.menu-preview__menu /deep/ .el-submenu .el-menu {
  margin: 2px 0 10px;
  border: none;
  background: transparent;
}

.menu-preview__menu /deep/ .el-submenu .el-menu-item {
  height: 42px;
  margin: 4px 0 0 12px;
  padding-left: 18px !important;
  border: 1px solid transparent;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.05);
  color: rgba(236, 244, 240, 0.68);
}

.menu-preview__menu /deep/ .el-submenu .el-menu-item:hover {
  background: rgba(255, 255, 255, 0.09);
}

.menu-preview__menu /deep/ .el-submenu .el-menu-item.is-active {
  border-color: rgba(212, 170, 99, 0.26);
  background: linear-gradient(135deg, rgba(212, 170, 99, 0.22) 0%, rgba(47, 122, 105, 0.18) 100%);
}

.menu-preview__menu /deep/ .el-submenu__icon-arrow {
  margin-top: -5px;
  color: inherit;
}

@media (max-width: 1080px) {
  .menu-preview {
    height: auto;
    min-height: 0;
  }
}
</style>
