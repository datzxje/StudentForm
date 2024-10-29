package com.example.studentform

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare variables for each UI element
    private lateinit var etMSSV: EditText
    private lateinit var etHoTen: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var etEmail: EditText
    private lateinit var etSoDienThoai: EditText
    private lateinit var btnToggleCalendar: Button
    private lateinit var calendarView: CalendarView
    private lateinit var spinnerPhuongXa: Spinner
    private lateinit var spinnerQuanHuyen: Spinner
    private lateinit var spinnerTinhThanh: Spinner
    private lateinit var cbTheThao: CheckBox
    private lateinit var cbDienAnh: CheckBox
    private lateinit var cbAmNhac: CheckBox
    private lateinit var cbAgree: CheckBox
    private lateinit var btnSubmit: Button

    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        etMSSV = findViewById(R.id.etMSSV)
        etHoTen = findViewById(R.id.etHoTen)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        etEmail = findViewById(R.id.etEmail)
        etSoDienThoai = findViewById(R.id.etSoDienThoai)
        btnToggleCalendar = findViewById(R.id.btnToggleCalendar)
        calendarView = findViewById(R.id.calendarView)
        spinnerPhuongXa = findViewById(R.id.spinnerPhuongXa)
        spinnerQuanHuyen = findViewById(R.id.spinnerQuanHuyen)
        spinnerTinhThanh = findViewById(R.id.spinnerTinhThanh)
        cbTheThao = findViewById(R.id.cbTheThao)
        cbDienAnh = findViewById(R.id.cbDienAnh)
        cbAmNhac = findViewById(R.id.cbAmNhac)
        cbAgree = findViewById(R.id.cbAgree)
        btnSubmit = findViewById(R.id.btnSubmit)

        calendarView.visibility = View.GONE

        setupSpinners()

        // Toggle calendar visibility
        btnToggleCalendar.setOnClickListener {
            if (calendarView.visibility == View.GONE) {
                calendarView.visibility = View.VISIBLE
            } else {
                calendarView.visibility = View.GONE
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            calendarView.visibility = View.GONE
            Toast.makeText(this, "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }

        btnSubmit.setOnClickListener {
            handleSubmit()
        }
    }

    private fun setupSpinners() {
        val phuongXaList = arrayOf("Phường 1", "Phường 2", "Phường 3")
        val quanHuyenList = arrayOf("Quận 1", "Quận 2", "Quận 3")
        val tinhThanhList = arrayOf("Hà Nội", "TP.HCM", "Đà Nẵng")

        val adapterPhuongXa = ArrayAdapter(this, android.R.layout.simple_spinner_item, phuongXaList)
        adapterPhuongXa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPhuongXa.adapter = adapterPhuongXa

        val adapterQuanHuyen = ArrayAdapter(this, android.R.layout.simple_spinner_item, quanHuyenList)
        adapterQuanHuyen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerQuanHuyen.adapter = adapterQuanHuyen

        val adapterTinhThanh = ArrayAdapter(this, android.R.layout.simple_spinner_item, tinhThanhList)
        adapterTinhThanh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTinhThanh.adapter = adapterTinhThanh
    }

    private fun handleSubmit() {
        val mssv = etMSSV.text.toString()
        val hoTen = etHoTen.text.toString()
        val genderId = radioGroupGender.checkedRadioButtonId
        val gender = if (genderId != -1) {
            findViewById<RadioButton>(genderId).text.toString()
        } else {
            "Not specified"
        }
        val email = etEmail.text.toString()
        val soDienThoai = etSoDienThoai.text.toString()
        val phuongXa = spinnerPhuongXa.selectedItem.toString()
        val quanHuyen = spinnerQuanHuyen.selectedItem.toString()
        val tinhThanh = spinnerTinhThanh.selectedItem.toString()
        val hobbies = mutableListOf<String>()
        if (cbTheThao.isChecked) hobbies.add("Thể thao")
        if (cbDienAnh.isChecked) hobbies.add("Điện ảnh")
        if (cbAmNhac.isChecked) hobbies.add("Âm nhạc")

        // Validation
        var isValid = true
        if (mssv.isBlank()) {
            etMSSV.error = "Please input this field"
            isValid = false
        } else {
            etMSSV.error = null
        }

        if (hoTen.isBlank()) {
            etHoTen.error = "Please input this field"
            isValid = false
        } else {
            etHoTen.error = null
        }

        if (email.isBlank()) {
            etEmail.error = "Please input this field"
            isValid = false
        } else {
            etEmail.error = null
        }

        if (soDienThoai.isBlank()) {
            etSoDienThoai.error = "Please input this field"
            isValid = false
        } else {
            etSoDienThoai.error = null
        }

        // Check agreement
        if (!cbAgree.isChecked) {
            Toast.makeText(this, "Please agree to the terms", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (!isValid) {
            return
        }

        val message = """
        MSSV: $mssv
        Họ tên: $hoTen
        Giới tính: $gender
        Email: $email
        Số điện thoại: $soDienThoai
        Ngày sinh: $selectedDate
        Phường/Xã: $phuongXa
        Quận/Huyện: $quanHuyen
        Tỉnh/Thành: $tinhThanh
        Sở thích: ${hobbies.joinToString(", ")}
    """.trimIndent()

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
