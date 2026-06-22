<template>
  <div v-if="levelList.length" class="breadcrumb-preview">
    <span class="breadcrumb-preview__label">当前位置</span>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item v-for="(item, index) in levelList" :key="item.path">
        <span v-if="index === levelList.length - 1" class="breadcrumb-preview__current">{{ item.name }}</span>
        <a v-else href="javascript:void(0)" @click.prevent="handleLink(item)">{{ item.name }}</a>
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  data() {
    return {
      levelList: []
    };
  },
  watch: {
    $route() {
      this.getBreadcrumb();
    }
  },
  created() {
    this.getBreadcrumb();
  },
  methods: {
    getBreadcrumb() {
      const home = {
        path: '/',
        name: '系统首页'
      };
      const matched = this.$route.matched || [];
      const levels = [home];

      matched.forEach(item => {
        if (!item || !item.path || item.path === '/' || item.path === '/index') {
          return;
        }
        const name = item.name || (item.meta && item.meta.title);
        if (name) {
          levels.push({
            path: item.path,
            name,
            redirect: item.redirect
          });
        }
      });

      const uniqueMap = {};
      this.levelList = levels.filter(item => {
        if (uniqueMap[item.path]) {
          return false;
        }
        uniqueMap[item.path] = true;
        return true;
      });
    },
    handleLink(item) {
      if (item.path === this.$route.path) {
        return;
      }
      this.$router.push(item.redirect || item.path);
    }
  }
};
</script>

<style lang="scss" scoped>
.breadcrumb-preview {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 16px 22px;
  border: 1px solid rgba(33, 79, 70, 0.1);
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.82);
  box-shadow: 0 12px 28px rgba(32, 54, 48, 0.08);
  backdrop-filter: blur(16px);
}

.breadcrumb-preview__label {
  flex-shrink: 0;
  color: #6a7f78;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.breadcrumb-preview /deep/ .el-breadcrumb__separator,
.breadcrumb-preview /deep/ .el-breadcrumb__inner,
.breadcrumb-preview /deep/ .el-breadcrumb__inner a {
  color: #546963;
}

.breadcrumb-preview /deep/ .el-breadcrumb__inner a:hover {
  color: #2f7a69;
}

.breadcrumb-preview__current {
  color: #203630;
  font-weight: 700;
}

@media (max-width: 768px) {
  .breadcrumb-preview {
    align-items: flex-start;
    flex-direction: column;
    gap: 8px;
  }
}
</style>
