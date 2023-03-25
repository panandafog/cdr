# cdr reports generator

Generates reports from comma-separated calls file cdr.txt

Report example: 
```
Tariff: SIMPLE
Phone number: 70020637023
–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 | Call type | Start time          | End time            | Duration | Cost | 
–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 | INCOMING  | 2023-02-14 03:49:17 | 2023-02-14 03:56:06 | 0:06:49  | 3.0  | 
 | INCOMING  | 2023-02-26 05:39:23 | 2023-02-26 05:50:26 | 0:11:03  | 5.5  | 
 | OUTGOING  | 2023-07-14 01:02:23 | 2023-07-14 01:09:16 | 0:06:53  | 3.0  | 
 | OUTGOING  | 2023-02-03 18:23:11 | 2023-02-03 18:25:14 | 0:02:03  | 1.0  | 
 | INCOMING  | 2023-02-21 07:09:28 | 2023-02-21 07:18:35 | 0:09:07  | 4.5  | 
 | INCOMING  | 2023-09-14 22:41:12 | 2023-09-14 22:45:15 | 0:04:03  | 2.0  | 
 | INCOMING  | 2023-08-05 02:53:25 | 2023-08-05 02:54:22 | 0:00:57  | 0.0  | 
 | INCOMING  | 2023-11-09 20:27:26 | 2023-11-09 20:28:18 | 0:00:52  | 0.0  | 
 | INCOMING  | 2023-12-11 05:11:30 | 2023-12-11 05:18:38 | 0:07:08  | 3.5  | 
 | OUTGOING  | 2023-11-15 21:35:51 | 2023-11-15 21:45:17 | 0:09:26  | 4.5  | 
 | OUTGOING  | 2023-05-05 15:54:13 | 2023-05-05 16:02:39 | 0:08:26  | 4.0  | 
 | INCOMING  | 2023-06-09 06:14:10 | 2023-06-09 06:15:34 | 0:01:24  | 0.5  | 
 | OUTGOING  | 2023-07-25 13:42:16 | 2023-07-25 13:52:00 | 0:09:44  | 4.5  | 
–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
total cost: 36.0 RUB

```