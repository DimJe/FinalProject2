package org.techtown.finalproject2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.R
import org.techtown.finalproject2.databinding.ActivityUserInfoBinding

class UserInfo : AppCompatActivity() {

    private val binding : ActivityUserInfoBinding by lazy{
        ActivityUserInfoBinding.inflate(layoutInflater)
    }
    private val api : Api by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.userInfo)
        initView()

    }

    override fun onRestart() {
        super.onRestart()
        initView()
    }
    private fun initView(){
        binding.userInfo.title = "${api.user?.user_NM}님의 정보"
        binding.userName.text = api.user?.user_NM
        binding.userNick.text = api.user?.user_NCKNM
        binding.userPhone.text = api.user?.user_TELNO
        binding.userRegion.text = api.user?.user_RGN
        binding.userHeight.text = api.user?.user_HGT.toString()
        binding.userWeight.text = api.user?.user_WGT.toString()
        binding.userGender.text = api.user?.user_GNDR
        binding.userAge.text = api.user?.user_AGE.toString()
        binding.userClub.text = if(api.user?.club_NO==0) "없음" else api.clubDataList.find { it.club_NO_PK == api.user?.club_NO }!!.club_NM
        binding.userManner.text = api.user?.user_MNR_SCR.toString()
        binding.userSkill.text = api.user?.user_SKL_SCR.toString()
        binding.userInstructor.text = if (api.user?.instr_USER_YN!!) "권한 있음" else "권한 없음"

        binding.registerInstr.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("강사 신청하기")
                .setMessage("매너 및 실력 점수가 4점 이상이어야 등록할 수 있습니다.")
                .setPositiveButton("신청하기"){_,_ ->
                    if(api.user?.user_MNR_SCR!! >= 4 && api.user?.user_SKL_SCR!! >= 4.0){
                       CoroutineScope(Dispatchers.IO).launch {
                           api.modifyUser(
                               api.user?.user_NM!!,api.user?.user_GNDR!!,api.user?.user_AGE!!,api.user?.user_NCKNM!!,
                               api.user?.user_RGN!!,api.user?.user_TELNO!!,api.user?.user_EML_ADDR!!,api.user?.user_HGT!!,
                               api.user?.user_WGT!!,true,api.user?.user_MNR_SCR!!,api.user?.user_SKL_SCR!!,api.user?.club_NO!!
                           )
                           api.getUser(api.user?.user_EML_ADDR!!)
                       }
                    }
                    else Toast.makeText(this, "점수가 충분하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("취소"){_,_ -> }
                .show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_modify,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Intent(this,ModifyUser::class.java).apply { startActivity(this) }

        return true
    }
}