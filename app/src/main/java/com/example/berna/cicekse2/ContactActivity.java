package com.example.berna.cicekse2;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ContactActivity extends Activity implements OnClickListener {

    TextView pagename;
    ImageButton geri;
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText name, mailadd, msg;
    TextView warning;
    String client_name, mail_address, client_message;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    static final String company_mail = "berna3594@gmail.com";
    static final String phone_number="+905435649013";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        pagename=(TextView)findViewById(R.id.pagename);
        pagename.setText("İletişim");
        context = this;
        geri = (ImageButton) findViewById(R.id.geri);
        Button send_btn = (Button) findViewById(R.id.send_btn);
        Button call_btn = (Button) findViewById(R.id.call_btn);
        name = (EditText) findViewById(R.id.name_et);
        mailadd = (EditText) findViewById(R.id.mail_et);
        msg = (EditText) findViewById(R.id.msg_et);
        warning=(TextView)findViewById(R.id.warning);
        warning.setVisibility(View.GONE);
        send_btn.setOnClickListener(this);

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /* call button event */
        call_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent make_call = new Intent(Intent.ACTION_CALL);
                make_call.setData(Uri.parse("tel:"+phone_number));
                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                startActivity(make_call);
            }
        });
    }

    @Override
    public void onClick(View view) {
        client_name = name.getText().toString();
        mail_address = mailadd.getText().toString().trim();
        client_message = msg.getText().toString();

        // onClick of button perform this simplest code.
        if (mail_address.matches(emailPattern))
        {
            warning.setVisibility(View.GONE);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(company_mail, "yesilcam");
                }
            });

            pdialog = ProgressDialog.show(context, "", "Gönderiliyor...", true);

            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();
        }
        else
        {
            warning.setVisibility(View.VISIBLE);
        }


    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(company_mail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("berna3594@gmail.com"));
                message.setSubject(client_name);
                message.setContent(client_message +"  Gönderen: "+ mail_address, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            name.setText("");
            mailadd.setText("");
            msg.setText("");
            openDialog();
        }

        public void openDialog() {
            final Dialog dialog = new Dialog(context); // Context, this, etc.
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.mail_dialog);
            dialog.setTitle("İletildi");
            TextView tvSampleText = (TextView) dialog.findViewById(R.id.custom_dialog_tv_text);
            tvSampleText.setText("Mesajınız tarafımıza ulaşmıştır");
            dialog.show();
            Button btnDone = (Button) dialog.findViewById(R.id.dialog_btn_done);

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }


}

