CREATE TABLE bslogi_sandbox.charges (
	id char(36) NOT NULL,
	agent_id char(36) NULL,
	name varchar(500) NOT NULL,
	charge_type varchar(50) NOT NULL,
	range_from int null,
	range_to int null,
	status varchar(25) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;





alter table container
	add tare_weight double NOT NULL default 0,
	add max_gross_weight double NOT NULL default 0;
	
update container set container_status = 'AVAILABLE' where container_status = 'EMPTY';

update container set container_status = 'REPAIR' where container_status = 'EMPTY_ESTIMATED';

update container set container_status = 'IN_TRANSIT' where container_status = 'EXPORTED';


alter table container_freight_station
add no_of_free_days int not null default 0;

alter table port_terminal
add no_of_free_days int not null default 0;

alter table depot
add no_of_free_days int not null default 0;


CREATE TABLE `agent_chat_history` (
  `id` char(36) NOT NULL,
  `sender_id` char(36) NOT NULL,
  `receiver_id` char(36) NOT NULL,
  `message` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `parent_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `agent_chat_history_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `agent_chat_history` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




ALTER TABLE charge
ADD COLUMN port_id char(36) AFTER agent_id;


ALTER TABLE charge ADD COLUMN is_terminal_based_charge bool default false;


CREATE TABLE `terminal_local_charges` (
  `port_import_export_charge_id` char(36) NOT NULL,
  `terminal_id` char(36) NOT NULL,
  `product_id` char(36) NOT NULL,
  `collection` double NOT NULL,
  `expense` double NOT NULL,
  `terminal_name` char(255) NOT NULL,
  PRIMARY KEY (`terminal_id`,`product_id`,`port_import_export_charge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


---------------------------------------------



ALTER TABLE port ADD COLUMN port_name_in_bl VARCHAR(100)  DEFAULT NULL AFTER name;

ALTER TABLE booking_freight_cargo MODIFY hs_code varchar(100) NULL; 
ALTER TABLE booking_freight_cargo MODIFY package_type_id char(36) NULL;
ALTER TABLE booking_freight_cargo MODIFY no_of_package int(10) NULL;
ALTER TABLE booking_freight_cargo MODIFY unit_type varchar(50) NULL;
ALTER TABLE booking_freight_cargo MODIFY gross_weight double NULL;

ALTER TABLE booking_freight_hdr ADD exp_haluage_payment_agent char(36) NULL;
ALTER TABLE booking_delivery_order ADD imp_haluage_payment_agent char(36) NULL;

alter table booking_bill_of_lading ADD COLUMN forwarder_comp_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN forwarder_tax_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN forwarder_email varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN forwarder_phone varchar(20) NULL; 
  alter table booking_bill_of_lading ADD COLUMN shipper_comp_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN shipper_tax_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN shipper_email varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN shipper_phone varchar(20) NULL;
  alter table booking_bill_of_lading ADD COLUMN consignee_comp_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN consignee_tax_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN consignee_email varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN consignee_phone varchar(20) NULL;
  alter table booking_bill_of_lading ADD COLUMN notify_party_comp_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN notify_party_tax_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN notify_party_email varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN notify_party_phone varchar(20) NULL;
  alter table booking_bill_of_lading ADD COLUMN second_notify_comp_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN second_notify_tax_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN second_notify_email varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN second_notify_phone varchar(20) NULL;
  alter table booking_bill_of_lading ADD COLUMN payment_party_comp_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN payment_party_tax_reg_no varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN payment_party_email varchar(250) NULL;
  alter table booking_bill_of_lading ADD COLUMN payment_party_phone varchar(20) NULL;

 
alter table acct_invoice_item MODIFY charge_id char(36) NULL

alter table acct_bill_item MODIFY charge_id char(36) NULL

alter table booking_slot_vessel_schedule ADD pickup_terminal char(36) NULL;

alter table booking_slot_vessel_schedule ADD arrival_terminal char(36) NULL;


CREATE TABLE quotation_hdr (
	id char(36) NOT NULL,
	booking_id char(36),
	quotation_no char(36) NOT NULL,
	quotation_date datetime NOT NULL,
	quotation_status varchar(50) DEFAULT 'DRAFT' NOT NULL,	
	pol_port_id char(36) NOT NULL,
	pod_port_id char(36) NOT NULL,
	commodity_id char(36) DEFAULT NULL,
	customer_id char(36) DEFAULT NULL,
	vessel_id char(36) DEFAULT NULL,
	container_type_id char(36),
	agent_id char(36) DEFAULT NULL,
	quotation_currency_id char(36) NOT NULL,	
	estimated_arrival_days int(10),
	20gp_total_amount double NOT NULL,
	40gp_total_amount double NOT NULL,
	valid_from datetime,
	valid_to datetime,
	customer_address varchar(500) DEFAULT NULL,
	terms_condition varchar(5000) DEFAULT NULL,
	no_of_container int(10),
	export_haulage_type varchar(255),
	import_haulage_type varchar(255),
	cargo_type varchar(255),	
	status varchar(25) NOT NULL,
	created_by char(36) DEFAULT NULL,
	created_on datetime,
	modified_by char(36) DEFAULT NULL,
	modified_on datetime,
	deleted_by char(36) DEFAULT NULL,
	deleted_on datetime,	
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


 
CREATE TABLE quotation_dtl (
	id char(36) NOT NULL,
	port_id char(36) NULL,
	quotation_id char(36) NOT NULL,
	charge_type varchar(25) NOT NULL,
	cost_type varchar(25) DEFAULT NULL,
	charge_id char(36) NOT NULL,
	currency_id char(36) NOT NULL,
	20gp_tariff double NOT NULL,
	40gp_tariff double NOT NULL,
	cost double NOT NULL,
	cur_conv_rate double NOT NULL,
	sequence int(10) NOT NULL,
	charge_name varchar(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


update booking_bill_of_lading bl inner join booking b on b.id =bl.booking_id 
	set bl.issue_place = b.pol_id;

--CREATE TABLE quotation_hdr (
--	id char(36) NOT NULL,
--	booking_id char(36),
--	quotation_no char(36) NOT NULL,
--	quotation_date datetime NOT NULL,
--	quotation_status varchar(50) DEFAULT 'DRAFT' NOT NULL,	
--	pol_port_id char(36) NOT NULL,
--	pod_port_id char(36) NOT NULL,
--	commodity_id char(36) DEFAULT NULL,
--	customer_id char(36) DEFAULT NULL,
--	vessel_id char(36) DEFAULT NULL,
--	container_type_id char(36),
--	agent_id char(36) DEFAULT NULL,
--	quotation_currency_id char(36) NOT NULL,	
--	estimated_arrival_days int(10),
--	20gp_total_amount double NOT NULL,
--	40gp_total_amount double NOT NULL,
--	valid_from datetime,
--	valid_to datetime,
--	customer_address varchar(500) DEFAULT NULL,
--	terms_condition varchar(5000) DEFAULT NULL,
--	no_of_container int(10),
--	export_haulage_type varchar(255),
--	import_haulage_type varchar(255),
--	cargo_type varchar(255),	
--	status varchar(25) NOT NULL,
--	created_by char(36) DEFAULT NULL,
--	created_on datetime,
--	modified_by char(36) DEFAULT NULL,
--	modified_on datetime,
--	deleted_by char(36) DEFAULT NULL,
--	deleted_on datetime,	
--	PRIMARY KEY (id)
--) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
--
-- 
--CREATE TABLE quotation_dtl (
--	id char(36) NOT NULL,
--	port_id char(36) NULL,
--	quotation_id char(36) NOT NULL,
--	charge_type varchar(25) NOT NULL,
--	cost_type varchar(25) DEFAULT NULL,
--	charge_id char(36) NOT NULL,
--	currency_id char(36) NOT NULL,
--	20gp_tariff double NOT NULL,
--	40gp_tariff double NOT NULL,
--	cost double NOT NULL,
--	cur_conv_rate double NOT NULL,
--	sequence int(10) NOT NULL,
--	charge_name varchar(255),
--	PRIMARY KEY (id)
--) ENGINE=InnoDB DEFAULT CHARSET=latin1;



ALTER TABLE port ADD COLUMN country_prefix_no VARCHAR(10)  DEFAULT NULL AFTER name;
ALTER TABLE port ADD COLUMN location_prefix_no VARCHAR(10)  DEFAULT NULL AFTER name;

ALTER TABLE quotation_dtl ADD COLUMN terminal_id char(36) DEFAULT NULL AFTER quotation_id;

ALTER TABLE acct_invoice ADD COLUMN sub_total double DEFAULT 0.00 AFTER amount;

ALTER TABLE acct_invoice ADD COLUMN adjustment double DEFAULT 0.00 AFTER sub_total; 

ALTER TABLE acct_bill ADD COLUMN sub_total double DEFAULT 0.00 AFTER amount;

ALTER TABLE acct_bill ADD COLUMN adjustment double DEFAULT 0.00 AFTER sub_total;

RENAME TABLE invoice_payment_receipt TO acct_invoice_payment_receipt;

RENAME TABLE bill_payment_receipt TO acct_bill_payment_receipt;


ALTER TABLE acct_bill ADD COLUMN paid_amount double DEFAULT 0.00 AFTER adjustment;
ALTER TABLE acct_bill ADD COLUMN payable_amount double DEFAULT 0.00 AFTER paid_amount;
 

ALTER TABLE port_import_export_charge ADD COLUMN is_applicable_for_Quotation bool default false AFTER sequence;
UPDATE port_import_export_charge SET is_applicable_for_Quotation=true WHERE is_applicable_for_Quotation=false;

ALTER TABLE quotation_hdr ADD COLUMN export_notes varchar(200) AFTER customer_address;
ALTER TABLE quotation_hdr ADD COLUMN import_notes varchar(200) AFTER customer_address;
ALTER TABLE quotation_hdr ADD COLUMN shipment_term varchar(200) AFTER customer_address;

alter table acct_invoice
	add invoice_date date null;
	 
	
		alter table acct_invoice 
	drop column default_usd_conversion;
	
	
alter table acct_bill
add due_date date null,
add bill_date date null;
	
update acct_invoice set invoice_date = created_on;
update acct_bill set bill_date = created_on;


alter table booking
	add closed_date date null;


ALTER TABLE quotation_hdr CHANGE vessel_id vessel_schedule_id char(36);
ALTER TABLE quotation_hdr DROP COLUMN no_of_container;
ALTER TABLE quotation_hdr DROP COLUMN export_haulage_type;

ALTER TABLE quotation_hdr DROP COLUMN import_haulage_type;

ALTER TABLE quotation_hdr MODIFY COLUMN shipment_term char(36);


CREATE TABLE bslogi_sandbox.account_head (
	id char(36) NOT NULL,
	name varchar(250) NOT NULL,
	short_name varchar(100) NOT NULL,
	description varchar(1000) NOT NULL,
	currency_id char(36) NOT NULL,
	account_id char(36),
	status varchar(25) NOT NULL,
	created_by char(36),
	created_on datetime,
	modified_by char(36),
	modified_on datetime,
	deleted_by char(36),
	deleted_on datetime,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



update agent_settings set booking_suffix = 1;

update agent_settings set booking_do_prefix = '',booking_do_suffix=1;

update agent_settings set booking_bl_prefix = '',booking_bl_suffix=0;

update agent_settings set invoice_prefix = '21',invoice_suffix=1;

update agent_settings set bills_prefix = '21',bills_suffix=1;

update vsl_service_route set terminal_id='347ea944-83b2-4f26-bda4-7a6d91ed5545' where port_id='3bc42a04-075c-4236-9158-c1c96c12dc03';

update vsl_service_route set terminal_id='067e2de0-5141-423c-9377-8030557bd0fe' where port_id='b27f63f4-9f4b-4d45-803e-7b35a3ff9c8c';
	
update vsl_service_route set terminal_id='0f1ef9b3-77e2-4edb-a996-6b7cbef5fe41' where port_id='0f1ef9b3-77e2-4edb-a996-6b7cbef5fe41';

update vsl_service_route set terminal_id='4ba8ee62-0496-4429-baf5-34d8035a3b45' where port_id='0278afcc-271b-4508-803f-51cf4b7501f1';

update vsl_service_route set terminal_id='46d6c247-722d-4737-9abf-0496be32f4c5' where port_id='11e81668-c7eb-45cf-b027-3e9e689cb159';
	
update vsl_service_route set terminal_id='c0185712-4df8-49f8-8c6a-2b7bc55c593d' where port_id='3bb6e205-d031-4818-b967-d3194f32a5c8';

update vsl_service_route set terminal_id='fdc9b9e9-26e4-426f-8df8-1c7f26de1141' where port_id='f8e0607c-6f4a-4d0b-bd65-1ad1bff57e3f';

update vsl_service_route set terminal_id='93e6a975-a367-4334-8f27-c80b3c2e18ce' where port_id='b6f8fc91-68b4-4550-8551-7a3b02f4f2da';
	
update vsl_service_route set terminal_id='21687f4b-c899-474a-9952-16b96d07f6f8' where port_id='d83bf1c9-618e-4708-b04b-9ffe0d66dad6';

update vsl_service_route set terminal_id='23fdce35-5af0-422f-868e-c568491fe8c4' where port_id='a28de388-ddfe-4770-bf13-1e4ff051c25a';

update vsl_service_route set terminal_id='649c809f-0fd2-478d-95c1-ad16cac1bb24' where port_id='86f15c81-66d3-4237-bb29-6c4e7a9daacf';

update vsl_service_route set terminal_id='890a00b6-d88c-46d7-80f3-4f3b890e92f4' where port_id='8b570fff-1df2-4f72-8b5a-45165bb3dbe3';

update vsl_service_route set terminal_id='05b8cd86-deca-4c95-a6e8-0b9193dbd76f' where port_id='9d31d8b1-c1dd-449f-82b9-8bd08132d923';

update vsl_service_route set terminal_id='b5f9e410-82e9-45c2-879e-3c67411c6532' where port_id='a7997a96-0985-4c30-b54b-7f779f82e006';
	


alter table acct_invoice
MODIFY COLUMN inv_notes varchar(1000);

alter table acct_invoice
MODIFY COLUMN receipt_note varchar(1000);

alter table acct_bill
MODIFY COLUMN bill_notes varchar(1000);

alter table acct_bill
MODIFY COLUMN receipt_note varchar(1000);

ALTER TABLE quotation_hdr ADD 40hc_total_amount double DEFAULT 0.00 AFTER 40gp_total_amount;

ALTER TABLE quotation_dtl ADD 40hc_tariff double DEFAULT 0.00 AFTER 40gp_tariff;
 

ALTER TABLE booking_freight_hdr ADD imp_haluage_payment_agent char(36) NULL,
ADD type_of_movement char(36) NULL;

ALTER TABLE booking_delivery_order DROP imp_haluage_payment_agent;
 
CREATE TABLE otp (
  id char(36) NOT NULL,
  user_id char(36) NOT NULL,
  otp varchar(15) NOT NULL,
  generated_date datetime NOT NULL,
  status varchar(25) NOT NULL,
  created_by char(36) DEFAULT NULL,
  created_on datetime DEFAULT NULL,
  modified_by char(36) DEFAULT NULL,
  modified_on datetime DEFAULT NULL,
  deleted_by char(36) DEFAULT NULL,
  deleted_on datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

ALTER TABLE users ADD COLUMN force_password_change bool default false;
 

CREATE TABLE `booking_party_details` (
  `id` char(36) NOT NULL,
  `booking_id` char(36) NOT NULL,  
  `party_type` varchar(20) NOT NULL,
  `customer_id` char(36) NOT NULL, 
  `address_line1` varchar(250) NOT NULL,
  `address_line2` varchar(250) DEFAULT NULL,
  `city` varchar(250) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `country_id` char(36) NOT NULL,
  `email` varchar(250) NOT NULL,
  `phone_no` varchar(50) NOT NULL,
  `iec_code` varchar(250) DEFAULT NULL,
  `tax_reg_no` varchar(250) DEFAULT NULL, 
  `notes` varchar(50) DEFAULT NULL, 
  `created_by` char(36) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` char(36) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `cancelled_by` char(36) DEFAULT NULL,
  `cancelled_date` datetime DEFAULT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 

CREATE TABLE `soc_booking` (
  `id` char(36) NOT NULL,
  `booking_no` varchar(50) NOT NULL,
  `booking_status` varchar(50) NOT NULL,
  `cargo_ready_date` date DEFAULT NULL, 
  `poa_id` char(36) NOT NULL,
  `pol_id` char(36) NOT NULL,
  `pod_id` char(36) NOT NULL,
  `fd_id` char(36) NOT NULL,
  `booking_agent_id` char(36) DEFAULT NULL,
  `delivery_agent_id` char(36) DEFAULT NULL,  
  `shipped_on_board_by` char(36) DEFAULT NULL,
  `shipped_on_board_date` datetime DEFAULT NULL,
  `port_in_by` char(36) DEFAULT NULL,
  `port_in_date` date DEFAULT NULL,
  `all_cont_picked_by` char(36) DEFAULT NULL,
  `all_cont_picked_date` date DEFAULT NULL,
  `vessel_arrival_marked_by` char(36) DEFAULT NULL,
  `vessel_arrival_date` date DEFAULT NULL, 
  `fd_release_approval` varchar(50) DEFAULT NULL,
  `fd_release_approval_by` char(36) DEFAULT NULL,
  `fd_release_approval_date` date DEFAULT NULL,
  `created_by` char(36) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` char(36) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `cancelled_by` char(36) DEFAULT NULL,
  `cancelled_date` datetime DEFAULT NULL,
  `quotation_id` char(36) DEFAULT NULL,
  `closed_date` date DEFAULT NULL,
   `delivery_type` varchar(50) DEFAULT NULL,
    `notes` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
CREATE TABLE haulage_charge (
  id char(36) NOT NULL,
  land_port_id char(36) NOT NULL,
  sea_port_id char(36) NOT NULL,
  mt_from int  NOT NULL,
  mt_to int  NOT NULL,
  container_type varchar(30) NOT NULL,
  hazardous_rate double NOT NULL,
  non_hazardous_rate double NOT NULL,
  status varchar(25) NOT NULL,
  created_by char(36) DEFAULT 'NULL',
  created_on datetime DEFAULT NULL,
  modified_by char(36) DEFAULT 'NULL',
  modified_on datetime DEFAULT NULL,
  deleted_by char(36) DEFAULT 'NULL',
  deleted_on datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 

ALTER TABLE users ADD COLUMN force_password_change bool default false;
payment_location




ALTER TABLE customer ADD COLUMN city VARCHAR(50) DEFAULT null AFTER customer_name;

ALTER TABLE soc_booking MODIFY booking_agent_id char(36) NOT NULL;




 CREATE TABLE `soc_booking_bill_of_lading` (
  `id` char(36) NOT NULL,
  `booking_id` char(36) NOT NULL,
  `bl_no` varchar(100) NOT NULL,
  `bl_type` varchar(50) NOT NULL,
  `bl_status` varchar(100) NOT NULL,
  `vessel_id` char(36) DEFAULT NULL,
  `vessel_name` varchar(200) DEFAULT NULL,
  `voyage` varchar(200) DEFAULT NULL,  
  `delivery_agent_id` char(36) DEFAULT NULL,
  `delivery_agent_address` varchar(200) DEFAULT NULL,
  `transhipment_agent_id` char(36) DEFAULT NULL,
  `transhipment_agent_address` varchar(200) DEFAULT NULL,
  `pol` varchar(100) DEFAULT NULL,
  `pod` varchar(100) DEFAULT NULL,
  `poa` varchar(100) DEFAULT NULL,
  `fd` varchar(100) DEFAULT NULL, 
  `description` varchar(2500) DEFAULT NULL,
  `pod_free_days` int(10) DEFAULT NULL,
  `pod_free_days_note` varchar(100) DEFAULT NULL,
  `type_of_movement` varchar(200) DEFAULT NULL,
  `total_original_copy` varchar(50) DEFAULT NULL,
  `issue_place` varchar(100) DEFAULT NULL,
  `issue_date` date DEFAULT NULL,
  `sign_as` varchar(50) DEFAULT NULL,
  `signing_agent` varchar(100) DEFAULT NULL,
  `reference_no` varchar(100) DEFAULT NULL,
  `agent_ref` varchar(100) DEFAULT NULL,
  `bl_category` varchar(20) NOT NULL DEFAULT 'MAIN',
  `switch_agent` char(36) DEFAULT NULL,
  `switch_charge_collecting_agent` char(36) DEFAULT NULL,
  `switched_date` date DEFAULT NULL,
  `switched_by` char(36) DEFAULT NULL,
  `locked_by` char(36) DEFAULT NULL,
  `locked_on` date DEFAULT NULL,
  `released_by` char(36) DEFAULT NULL,
  `released_on` date DEFAULT NULL,
  `status` varchar(25) DEFAULT NULL,
  `created_by` char(36) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` char(36) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `deleted_by` char(36) DEFAULT NULL,
  `deleted_on` datetime DEFAULT NULL,    
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



  ALTER TABLE  booking_delivery_order ADD COLUMN issue_place char(36) DEFAULT NULL AFTER do_no;

 alter table container_damange_part drop column damage_size;
 
  alter table container_damange_part drop column man_hrs;
 
 ALTER TABLE soc_booking ADD COLUMN booking_type varchar(50) NULL AFTER booking_no;
 
 ALTER TABLE booking_cro ADD COLUMN depo_name varchar(250) AFTER depot_id;
 
 ALTER TABLE booking_containers MODIFY container_id char(36) NULL;
 
 ALTER TABLE booking_containers MODIFY pickup_depo_id char(36) NULL;

ALTER TABLE booking_containers MODIFY cro_no varchar(36) NULL;

ALTER TABLE booking_containers MODIFY container_grp_name varchar(36) NULL



CREATE TABLE `coc_booking_master_bill_of_lading` (
  `id` char(36) NOT NULL,
  `booking_id` char(36) NOT NULL,
  `mbl_no` varchar(100) NOT NULL,
  `file_url` varchar(1000) DEFAULT NULL,
  `type` varchar(100) NOT NULL,
  `sob_date` datetime NOT NULL,
  `issue_date` datetime NOT NULL,  
  `status` varchar(25) NOT NULL,
  `created_by` char(36) DEFAULT 'NULL',
  `created_on` datetime DEFAULT NULL,
  `modified_by` char(36) DEFAULT 'NULL',
  `modified_on` datetime DEFAULT NULL,
  `deleted_by` char(36) DEFAULT 'NULL',
  `deleted_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `coc_booking_deleivery_order` (
  `id` char(36) NOT NULL,
  `booking_id` char(36) NOT NULL,
  `do_no` varchar(100) NOT NULL,
   `bl_no` varchar(100) NOT NULL,
  `file_url` varchar(1000) DEFAULT NULL,
  `type` varchar(100) NOT NULL,
  `arrival_date` datetime NOT NULL,
  `issue_date` datetime NOT NULL, 
  `status` varchar(25) NOT NULL,
  `created_by` char(36) DEFAULT 'NULL',
  `created_on` datetime DEFAULT NULL,
  `modified_by` char(36) DEFAULT 'NULL',
  `modified_on` datetime DEFAULT NULL,
  `deleted_by` char(36) DEFAULT 'NULL',
  `deleted_on` datetime DEFAULT NULL,  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `booking_document` (
  `id` char(36) NOT NULL,
  `booking_id` char(36) NOT NULL,
  `booking_no` varchar(100) NOT NULL,
 `file_type` varchar(100) DEFAULT NULL, 
  `title` varchar(100) NOT NULL,
  `description` varchar(500) NULL,  
  `file_name` varchar(500) DEFAULT NULL,  
  `status` varchar(25) NOT NULL,
  `created_by` char(36) DEFAULT 'NULL',
  `created_on` datetime DEFAULT NULL,
  `modified_by` char(36) DEFAULT 'NULL',
  `modified_on` datetime DEFAULT NULL,
  `deleted_by` char(36) DEFAULT 'NULL',
  `deleted_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE port_import_export_charge ADD COLUMN vendor_id char(36) DEFAULT NULL AFTER port_id;


alter table soc_booking_bill_of_lading 
add column marks_nos varchar(1000);

ALTER TABLE booking_delivery_order ADD COLUMN de_stuffing_type VARCHAR(250) DEFAULT NULL AFTER delivery_type



CREATE TABLE `booking_notes` (
  `id` char(36) NOT NULL,
  `booking_id` char(36) NOT NULL,
  `note_type` varchar(25) NOT NULL,
  `notes` varchar(2500) NOT NULL,
  `created_by` char(36) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` char(36) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `deleted_by` char(36) DEFAULT NULL,
  `deleted_on` datetime DEFAULT NULL,
  `status` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



 CREATE TABLE `port_notes_terms` (
  `port_id` char(36) NOT NULL,
  `import_notes` varchar(10000) NOT NULL,
  `export_notes` varchar(10000) NOT NULL,
  `inv_terms` varchar(10000) NULL,
  `bill_terms` varchar(10000) NULL,
  `cro_terms` varchar(10000) NULL,
  PRIMARY KEY (`port_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;