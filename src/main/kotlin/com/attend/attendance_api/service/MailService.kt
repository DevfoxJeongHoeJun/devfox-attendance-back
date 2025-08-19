package com.attend.attendance_api.service

import com.attend.attendance_api.dto.MailRequest
import jakarta.mail.internet.MimeMessage
import org.springframework.stereotype.Service
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper


@Service
class MailService(
    private val mailSender: JavaMailSender
) {

    fun sendInviteMail(req: MailRequest) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        helper.setTo(req.toMail) // 受信先
        helper.setSubject("招待状が届きました！") // タイトル
        helper.setFrom("jeonghj@gmail.com", req.groupName) // 送信先、ネーム

        // HTML 本文
        val html = """
                <html>
                <body>
                    <p>こんにちは！</p>
                    <p>以下のボタンをクリックして、グループに参加してください：</p>
                    <br/>
                    <a href="${req.inviteUrl}" 
                       style="
                           display: inline-block;
                           padding: 12px 24px;
                           background-color: #1E90FF;
                           color: white;
                           text-decoration: none;
                           border-radius: 6px;
                           font-weight: bold;
                       ">
                       グループに参加する
                    </a>
                    <br/><br/>
                    <p>よろしくお願いいたします。</p>
                </body>
                </html>
            """.trimIndent()

        helper.setText(html, true) // true = HTML モード
        mailSender.send(mimeMessage)
    }
}