@rem ##########################################################################
@rem  Gradle startup script for Windows
@rem ##########################################################################

@if "%DEBUG%"=="" @echo off
@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

where gradle >nul 2>nul
if %ERRORLEVEL% equ 0 (
    echo Found local Gradle installation. Building Mystic Arts Mod...
    gradle %*
    goto end
)

set WRAPPER_JAR="%APP_HOME%gradle\wrapper\gradle-wrapper.jar"
if exist %WRAPPER_JAR% (
    java -jar %WRAPPER_JAR% %*
    goto end
)

echo =====================================================================
echo  Building Mystic Arts Fabric Mod 1.20.1
echo =====================================================================
echo  Notice: No global 'gradle' binary was found in your PATH.
echo.
echo  EASIEST OPTION - FREE GITHUB CLOUD BUILD:
echo   1. Push or upload this project to any GitHub repository.
echo   2. The included .github/workflows/build.yml automatically compiles
echo      mysticarts-1.0.0.jar in 60 seconds on GitHub!
echo   3. Download the artifact and drop into Prism or CurseForge.
echo =====================================================================
pause

:end
@rem End local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" endlocal
