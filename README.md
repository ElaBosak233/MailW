# MailW
[![AppVeyor](https://img.shields.io/appveyor/build/ElaBosak233/MailW?label=Appveyor%20Build&style=flat-square)](https://ci.appveyor.com/project/ElaBosak233/mailw) ![release](https://img.shields.io/github/v/release/ElaBosak233/MailW?style=flat-square) ![author](https://img.shields.io/badge/author-ElaBosak233-brightgreen?style=flat-square) ![Bukkit](https://img.shields.io/badge/Bukkit-1.12.2+-blue?style=flat-square)

## ⭐**WHAT CAN IT DO**

Have you ever imagined linking the whitelist and activation code? Have you ever thought about delivering weekly reports to players? Have you ever thought of sending banned players a banned notification? ...Now, **MailW** can help you solve these problems. **MailW** is a pre-plugin that supports custom html mail. With only a little Bukkit programming experience, you can continue to develop on the basis of **MailW** to achieve your unachieved goals. To help you better manage your server, **MailW** supports the use of specific placeholders to replace variables in the `Bukkit API` to easily embed player names and other content into emails.

## ⭐**COMMANDS**

### **MailW**
```yaml
## If you want to use these commands, you are supposed to have permission "MailW.general"(Give all by default)
/MailW set <Email> -- Record MailW mailbox
/MailW remove -- Remove MailW mailbox records
```
### **MailWController**
```yaml
## If you want to use these commands, you are supposed to have permission "MailW.admin"(Give op by default)
/MailWController set <Email> <SMTP> <Port> <Password> -- Set the sending email
/MailWController send <Player> <Template> -- Send specified template emails to specific players
```

## ⭐**EMAIL DESIGN TUTORIAL**

1. **Create** a folder in the `/plugins/MailW/template` directory, named `example`, and create a new file named `index.html` in the folder.
2. **Use** notepad (more professional editor is better) to open `index.html`.
3. **Enter** the following code.
```html
<!--Specifies that files are encoded in UTF-8 format-->
<head>
    <meta charset="UTF-8">
</head>
<!--Set the email subject based on the content in <title>...</title>-->
<title>Example</title>
<!--Set the email subject based on the content in <sender hideen>...</sender>-->
<sender hidden>YourName</sender>
<!--Since it is an email, the contents of <titile>...</title> and <sender hidden>...</sender> are not visible, and the rest of the content will be judged as the body of the email, you can use inline style-->
<body>
<h1>{{player.name}}</h1>, If you see this email, it means that <b>MailW</b> has been able to send mail normally on your server
</body>
```
4. **Enter** the server and enter the command `/MailWController send <YourName> example` (If you have never set up **MailW** or you have never set up your own mailbox, please follow the **COMMANDS** section to set up before you send the example email).
5. **Open** your mailbox, you can see the mail sent by MailW to you.

## ⭐**DOWNLOAD**
|**Version**|**Bukkit**|**Content**|**Date**|**Address**
|:-:|:-:|:-:|:-:|:-:|
|**1.3.0**|1.13+|1.13+ Supported|2020.7.13|[**Download**](https://github.com/ElaBosak233/MailW/releases/download/1.3.0/MailW-1.3.0.jar)|
|**1.2.0**|1.12.2|Bug fixes, support sending emails to offline players|2020.7.7|[**Download**](https://github.com/ElaBosak233/MailW/releases/download/1.2.0/MailW-1.2.0.jar)|
|**1.1.0**|1.12.2|Simplify plugin commands and update API|2020.7.7|[**Download**](https://github.com/ElaBosak233/MailW/releases/download/1.1.0/MailW-1.1.0.jar)|
|**1.0.0**|1.12.2|Init Project|2020.7.2|[**Download**](https://github.com/ElaBosak233/MailW/releases/download/1.0.0/MailW.jar)|

## ⭐**FAQ**
- **Why** the **Chinese** email I send is garbled
> That is because the **default** encoding follows the system encoding when using the **JVM**, please add `-Dfile.encoding=utf-8` after `java` in the **startup script**, but this will result in garbled characters on your server terminal, so I suggest to use the form of pictures instead of Chinese and full-width characters
- **Why** did I enter the correct email password, but still can't send email?
> Some mailboxes do not use passwords as SMTP sending passwords and require `authorization codes`
- **Why** ordinary port 25 is not available in some mailboxes
> For example, the SMTP service of Ali mailbox, port 25 is `blocked`, you need to use `SSL support` port 465
- **How many** placeholders support MailW and what are they
> You can see them in [**HERE**](https://github.com/ElaBosak233/MailW/blob/master/src/main/java/cn/elabosak/mailw/API/MailWAPI.java#L138), for example, if you enter `{{player.uuid}}` in your template file, it will be replaced with `the uuid of the target player`, which is very flexible
- **How can** I rely on MailW for development
> See [**Development Manual**](https://github.com/ElaBosak233/MailW/wiki/Development-Manual) in **Wiki** for details