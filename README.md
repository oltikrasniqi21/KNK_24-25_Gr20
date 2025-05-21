# KNK_25_Gr20 - Sistemi i Menaxhimit të Bursave për Studentë

Ky projekt është një sistem interaktiv i zhvilluar në Java (JavaFX) i cili lehtëson procesin e aplikimit dhe menaxhimit të bursave për studentët.
Përdoruesit ndahen në dy role: student dhe administrator. Sistemi do të mundësojë:

- Aplikimi për bursa në mënyrë të strukturuar përmes një forme të lehtë për përdorim.
- Njoftime automatike për aplikimet, statuset dhe vendimet e marra.
- Shfaqja e statusit të aplikimeve për studentët në kohë reale.
- Panel administrativ për menaxhimin e aplikimeve dhe shqyrtimin e dokumenteve.
- Formular për feedback, për përmirësimin e vazhdueshëm të sistemit.
- Ndërfaqe shumëgjuhëshe (multilingual) për studentë të huaj ose nga diaspora.

---------------------------------------------------------
## Teknologjitë e Përdorura
- Java - Gjuha kryesore e programimit
- JavaFX - Për ndërfaqen grafike (GUI)
- SceneBuilder - Për dizajnimin vizual të skenave
- PostgreSQL - Si databazë për ruajtjen e të dhënave

-----------------------------------------------------------------

## Mënyra e Përdorimit
### Për Administratorët
1. Gjenerimi i Super-Adminit 
- Hapi i parë: Pas ekzekutimit të projektit për herë të parë, duhet të krijohet një llogari Super Admin. 
- Super Admini ka privilegje të plota dhe mund të krijojë adminë të tjerë për sistemin.

2. Menaxhimi i Përdoruesve 
- Vetëm adminët kanë qasje në menunë "Menaxhimi i Përdoruesve", ku mund të:
- Shtojnë administratorë të tjerë.
- Verifikojnë dhe aktivizojnë llogaritë e studentëve të regjistruar.

3. Menaxhimi i Institucioneve Akademike
- Adminët mund të:
- Shtojnë universitetet.
- Regjistrojnë fakultetet përkatëse.
- Definojnë drejtimet akademike të disponueshme për aplikim në bursa.

4. Ndihmë dhe Njoftime
- FAQ (Frequently Asked Questions): Adminët mund të krijojnë dhe përditësojnë pyetjet më të shpeshta për të ndihmuar përdoruesit e rinj. 
- Njoftime të Përgjithshme: Mund të dërgojnë njoftime masive për të gjithë studentët e regjistruar. 
- Postimi i Lajmeve: Adminët mund të publikojnë lajme të rëndësishme për studentët në seksionin e dedikuar.

5. Menaxhimi i Feedback-ut 
- Adminët pranojnë komente/feedback nga studentët dhe kanë mundësinë t’u përgjigjen përmes sistemit.

6. Menaxhimi i Aplikimeve për Bursa
   - Pranojnë, refuzojnë ose aprovojnë aplikimet për bursa. 
   - Gjenerojnë dhe dërgojnë njoftime të personalizuara për secilin student lidhur me statusin e aplikimit.


### Për Studentët
1. Regjistrimi (Sign Up)
- Studentët mund të krijojnë llogari përmes një email-i valid universitar/studentor. 
- Llogaria nuk është aktive derisa të verifikohet nga një admin.

2. Kyçja (Login)
- Pas verifikimit nga admini, studentët mund të kyçen në sistem dhe të përdorin të gjitha funksionalitetet e ofruara.

3. Aplikimi për Bursa
- Mund të shikojnë bursa të disponueshme dhe të aplikojnë përmes një forme të thjeshtë dhe të strukturuar.
- Marrin njoftime automatike për statusin e aplikimeve.

4. Funksione Shtesë për Studentët
- Lexojnë lajmet e publikuara nga administrata. 
- Marrin dhe lexojnë njoftime të përgjithshme ose personale. 
- Konsultojnë seksionin e FAQ për ndihmë dhe informacione të përgjithshme. 
- Dërgojnë feedback ose komente rreth përvojës së tyre në platformë. 
- Mund të modifikojnë informacionin personal nga profili i tyre.



---------------------------------------------------------------
## Ekipi Zhvillues
Ky projekt është zhvilluar nga grupi Gr. 20, në kuadër të lëndës "Komunikimi Njeri-Kompjuteri" (KNK), FIEK, UP:
- Bleron Baftiu
- Olsa Domi
- Olti Krasniqi
- Riga Zubaku
- Rreze Ejupi
- Valmir Mustafa

