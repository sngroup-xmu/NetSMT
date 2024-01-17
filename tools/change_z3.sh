#!/bin/sh

Z3_2019_DIR="z3-2019-01-26-be61a26452735dfe62b14ddb4804ca5df0526dcf-x64-ubuntu-18.04"
Z3_LATEST_DIR="z3-4.12.2"
Z3_2018_DIR="z3-4.8.5"
Z3_GUIDED_DIR="z3_guided"
change_z3_version(){
  echo 'please choose z3 version'
  echo '1: z3-2019'
  echo '2: z3-4.12.2'
  echo '3: z3-4.8.5'
  echo '4: z3_guided'
  read version
  case ${version} in
    1)
       Z3_DIR=${Z3_2019_DIR}
       ;;
    2)
       Z3_DIR=${Z3_LATEST_DIR}
       ;;
    3)
       Z3_DIR=${Z3_2018_DIR}
       ;;
   4)
       Z3_DIR=${Z3_GUIDED_DIR}
       ;;
    *)
       echo "unsupported param: ${version}"
       exit1
       ;;
  esac
  move_file_to_usr
}

move_file_to_usr(){
  INSTALL_PREFIX="/usr"
  BINDIR="${INSTALL_PREFIX}/bin"
  LIBDIR="${INSTALL_PREFIX}/lib"
  echo "Installing Z3 to ${INSTALL_PREFIX}"
  cp "${Z3_DIR}/lib/libz3.so" "${Z3_DIR}/lib/libz3java.so" "${LIBDIR}/"
  cp "${Z3_DIR}/bin/z3" "${BINDIR}/"
  strip "${LIBDIR}/libz3.so"
  strip "${LIBDIR}/libz3java.so"
  strip "${BINDIR}/z3"

}

change_z3_version
