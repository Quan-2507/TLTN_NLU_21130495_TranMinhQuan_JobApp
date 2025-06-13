import 'package:flutter/material.dart';
import '../common_widgets/bullet_list.dart';
import '../common_widgets/header_text.dart';


class TermConditionScreen extends StatelessWidget {
  const TermConditionScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Terms & Conditions',
            style: TextStyle(fontWeight: FontWeight.bold, color: Colors.white)),
        iconTheme: const IconThemeData(color: Colors.white),
        centerTitle: true,
        backgroundColor: Color(0xFF44903e),
        surfaceTintColor: Colors.white,
      ),
      backgroundColor: Colors.white,
      body: SingleChildScrollView(
        child: Container(
          margin: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const HeaderText('Acceptance of Terms & Conditions'),
              bodyText('Please read the Terms & Conditions carefully before accessing and using the DREAM JOB.com Career Website (hereinafter called the "DREAM JOB Website") of Navigos Group Vietnam Joint Stock Company (hereinafter called the "Company"). By accessing to and using DREAM JOB Website, you (hereinafter called as “You” or the “Customer”, unless the context otherwise requires) accept the Terms & Conditions and agree to abide by the rules governing the use of services offered by the DREAM JOB Website (hereinafter called the “Agreement”).'),
              const HeaderText('DREAM JOB Services'),
              bodyText('The DREAM JOB Website is a World Wide Web site on the Internet that is designed to allow users to post jobs and/or to view jobs posted by other users or interact with those users. The DREAM JOB Website is owned and operated by the Company. The DREAM JOB Website contains or may contain information, communications, opinions, text, graphics, links, electronic art, animations, audio, video, software, photos, music, sounds and other material and data (collectively, "Content") formatted, organized and collected in a variety of forms that are generally accessible to Users, including directories and databases, and areas of the DREAM JOB Website that can be modified by Users, such as posting jobs, uploading multimedia files, registering user profiles, and creating Auto-Notify profiles ("Interactive Areas").'),
              bodyText('To have the full use of services offered on the Website, you need to sign up to create an account and provide DREAM JOB Website with certain personal information including but not limited to your email address for further communications between you and DREAM JOB Website. By signing up, you agree to receive our newsletters, emails, messages, phone calls, and other communications about our products or services, in the event the laws impose any limitation on this matter and allow the parties to agree otherwise, you agree with the Company that such limitation shall not be applied. Whenever you wish not to receive them you can notify us with immediate effect using the unsubscribing function available on DREAM JOB Website or at the bottom of our emails. Any of your personal information that you submit will be treated in accordance with the Privacy Policy of DREAM JOB Website.'),
              const HeaderText('Provision for Use of Services'),
              const BulletList([
                'After you have paid the Service Fee and/or activate the Purchased Services, we will not accept any requests to terminate or cancel the Purchased Services, refund the Service Fee or change any service.',
                'You will create your own account with username and password to use the services on the DREAM JOB Website.',
                'You are entitled to post your recruitment advertisement or post a link on the DREAM JOB Website to the recruitment advertisement on your website. It is your responsibility to ensure the accuracy and legality of all these contents. However you are not allowed to add all formats of email address and/or phone number and/or fax number or any other form of contact information in Job Posting Content.',
                'You must activate the Purchased Services before their expiry date. You cannot activate any unused Services once they have expired.',
                'In the event that we are at fault and have not cured the problem within seven working days of receiving a written notice from you, we will refund the amount of Service Fee related to the unused services in the Purchase Order/Service Agreement where the Services have not been delivered as agreed by both parties.'
              ]),
            ],
          ),
        ),
      ),
    );
  }

  Text bodyText(String text){
    return Text(
      text,
      style: TextStyle(
        color: Colors.black.withOpacity(0.6),
        height: 1.55,
      ),
    );
  }
}
