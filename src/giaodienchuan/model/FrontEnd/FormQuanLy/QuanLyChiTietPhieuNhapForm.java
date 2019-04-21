/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;
import giaodienchuan.model.BackEnd.QuanLyChiTietPhieuNhap.QuanLyChiTietPhieuNhapBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiChiTietPN;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaChiTietPhieuNhapForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class QuanLyChiTietPhieuNhapForm extends JFrame{
    HienThiChiTietPN formHienThi;
    
    String mapn;
    String masp;
    
    JButton btnThem = new JButton("Thêm");
    JButton btnXoa = new JButton("Xóa");

    public QuanLyChiTietPhieuNhapForm(String _mapn) { // v cai tham so nay tren troi roi xuong a?
        setLayout(new BorderLayout());
        
        this.mapn = _mapn;
        this.setTitle("Chi tiết phiếu nhập " + this.mapn);
        formHienThi = new HienThiChiTietPN(this.mapn);
        // day truyen tham so cho hien thi ch itiet ne
        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);

        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.NORTH);

        // actionlistener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    private void btnXoaMouseClicked() {
        String masp = formHienThi.getSelectedChiTietPhieuNhap(2);
        if (masp != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm " + masp + " của phiếu nhập "+ this.mapn +"?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyChiTietPhieuNhapBUS().delete(this.mapn, masp);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn chi tiết nào để xóa");
        }
    }
    private void btnSuaMouseClicked() {
        String masp = formHienThi.getSelectedChiTietPhieuNhap(2);
        ThemSuaChiTietPhieuNhapForm themctpn = new ThemSuaChiTietPhieuNhapForm("Sửa", this.mapn,this.masp);
        themctpn.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                formHienThi.refresh();
            }
        });
    }
    private void btnThemMouseClicked() {
        ThemSuaChiTietPhieuNhapForm themcthd = new ThemSuaChiTietPhieuNhapForm("Thêm",this.mapn,"");
        themcthd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                formHienThi.refresh();
            }
        });
    }
}
