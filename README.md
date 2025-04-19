# カラス印のビルドファイル自動命名プラグイン 🪶

mcplugin-auto-jar は、Minecraft プラグイン開発向けに設計された Gradle プラグインで、ビルドされた JAR ファイルの命名を自動化します。
命名規則には plugin.yml や Git のブランチ情報、ハッシュ情報を取り入れ、開発・配布のバージョン管理をよりスムーズにします。

## ✨ 主な機能
plugin.yml の情報から自動でプラグイン名・バージョンを取得

Git ブランチ・コミットハッシュをファイル名に反映

Minecraft 対応バージョンを命名に含める

## 要件

- Java 17 以上
- Paperプラグイン開発環境

## 📦 出力されるJARの命名規則
形式:
`[projectName]-[major].[minor].[patch].[build]-[minecraftVersion]-[gitBranch]-[gitHash].jar`

例:
`KarasuPlugin-1.2.3.45-1.20.4-main-a1b2c3d.jar`

## 🚀 導入方法
`settings.gradle` にリポジトリを追加:

```gradle
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.pkg.github.com/karasu256/maj") }
    }
}
```

`build.gradle`にプラグインを適用:

```
plugins {
    id 'com.karasu256.maj' version '0.1.0'
}
```

## ⚙️ 環境変数の利用（公開時）
GitHub にプラグインを公開する際は、以下の環境変数を設定してください:

```
GRADLE_PUBLISH_KEY=your-key
GRADLE_PUBLISH_SECRET=your-secret
```

## 📁 plugin.yml について
このプラグインは src/main/resources/plugin.yml を自動的に読み取ります。
そこから name と version を取得します。

## 🧪 サンプルプロジェクト
`./maj/SamplePlugin`にサンプルプロジェクトがあります。

ローカルでこのプラグインを参照するには、相対パスで以下のように指定:

```gradle
buildscript {
    dependencies {
        classpath files('../maj/build/libs/mcplugin-auto-jar-0.1.0.jar')
    }
}
```

## 🛠 ビルド＆適用例

```gradle
cd maj
./gradlew publishToMavenLocal

cd SamplePlugin
./gradlew build
```
出力先：build/libs/
生成されたJARのファイル名が命名規則通りか確認してください。

🐦 作者
作成者: Hashibutogarasu

ライセンス: MIT
